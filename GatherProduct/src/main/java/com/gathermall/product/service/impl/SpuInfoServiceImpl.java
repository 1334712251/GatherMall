package com.gathermall.product.service.impl;

import com.gathermall.common.to.SkuReductionTo;
import com.gathermall.common.to.SpuBoundTo;
import com.gathermall.common.utils.R;
import com.gathermall.product.entity.*;
import com.gathermall.product.feign.OperationFeignService;
import com.gathermall.product.service.*;
import com.gathermall.product.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.common.utils.Query;

import com.gathermall.product.dao.SpuInfoDao;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Service("spuInfoService")
@Transactional
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoDao, SpuInfo> implements SpuInfoService {


    @Autowired
    private SpuInfoDescService spuInfoDescService;

    @Autowired
    private SpuImagesService spuImagesService;

    @Autowired
    private SkuImagesService skuImagesService;

    @Autowired
    private AttrService attrService;

    @Autowired
    private ProductAttrValueService productAttrValueService;

    @Autowired
    private SkuInfoService skuInfoService;

    @Autowired
    private SkuSaleAttrValueService skuSaleAttrValueService;

    @Autowired
    private OperationFeignService operationFeignService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SpuInfo> page = this.page(
                new Query<SpuInfo>().getPage(params),
                new QueryWrapper<SpuInfo>()
        );

        return new PageUtils(page);
    }

    /**
     * TODO 待测
     *
     * @param vo
     */
    @Override
    public void saveSpuInfo(SpuSaveVo vo) {

        //1、保存spu基本信息 pms_spu_info
        SpuInfo spuInfo = new SpuInfo();
        BeanUtils.copyProperties(vo, spuInfo);
        spuInfo.setCreateTime(new Date());
        spuInfo.setUpdateTime(new Date());
        this.saveBaseSpuInfo(spuInfo);

        //2、保存Spu的描述图片 pms_spu_info_desc
        List<String> decript = vo.getDecript();
        SpuInfoDesc descEntity = new SpuInfoDesc();
        descEntity.setSpuId(spuInfo.getId());
        descEntity.setDecript(String.join(",", decript));
        spuInfoDescService.saveSpuInfoDesc(descEntity);


        //3、保存spu的图片集 pms_spu_images
        List<String> images = vo.getImages();
        spuImagesService.saveImages(spuInfo.getId(), images);


        //4、保存spu的规格参数;pms_product_attr_value
        List<BaseAttrsVo> baseAttrs = vo.getBaseAttrs();
        List<ProductAttrValue> collect = baseAttrs.stream().map(attr -> {
            ProductAttrValue productAttrValue = new ProductAttrValue();
            productAttrValue.setAttrId(attr.getAttrId());
            Attr id = attrService.getById(attr.getAttrId());

            productAttrValue.setAttrName(id.getAttrName());
            productAttrValue.setAttrValue(attr.getAttrValues());
            productAttrValue.setQuickShow(attr.getShowDesc());
            productAttrValue.setSpuId(spuInfo.getId());

            return productAttrValue;
        }).collect(Collectors.toList());
        productAttrValueService.saveProductAttr(collect);


        //5、保存spu的积分信息；gulimall_sms->sms_spu_bounds
        //调用优惠券服务(即运营服务)
        BoundsVo boundsVo = vo.getBounds();
        SpuBoundTo spuBoundTo = new SpuBoundTo();
        BeanUtils.copyProperties(boundsVo, spuBoundTo);
        spuBoundTo.setSpuId(spuInfo.getId());
        R r = operationFeignService.saveSpuBounds(spuBoundTo);
        if (r.getCode() != 0) {
            try {
                throw new Exception("远程保存spu积分信息失败");
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.error("远程保存spu积分信息失败");
        }


        //5、保存当前spu对应的所有sku信息；

        List<SkusVo> skus = vo.getSkus();
        if (skus != null && skus.size() > 0) {
            skus.forEach(item -> {
                String defaultImg = "";
                for (ImagesVo image : item.getImages()) {
                    if (image.getDefaultImg() == 1) {
                        defaultImg = image.getImgUrl();
                    }
                }
                //    private String skuName;
                //    private BigDecimal price;
                //    private String skuTitle;
                //    private String skuSubtitle;
                SkuInfo skuInfo = new SkuInfo();
                BeanUtils.copyProperties(item, skuInfo);
                skuInfo.setBrandId(spuInfo.getBrandId());
                skuInfo.setCatalogId(spuInfo.getCatalogId());
                skuInfo.setSaleCount(0L);
                skuInfo.setSpuId(spuInfo.getId());
                skuInfo.setSkuDefaultImg(defaultImg);
                //5.1）、sku的基本信息；pms_sku_info
                skuInfoService.saveSkuInfo(skuInfo);

                Long skuId = skuInfo.getSkuId();

                List<SkuImages> imagesEntities = item.getImages().stream().map(img -> {
                    SkuImages skuImagesEntity = new SkuImages();
                    skuImagesEntity.setSkuId(skuId);
                    skuImagesEntity.setImgUrl(img.getImgUrl());
                    skuImagesEntity.setDefaultImg(img.getDefaultImg());
                    return skuImagesEntity;
                }).filter(entity -> {
                    //返回true就是需要，false就是剔除
                    return !StringUtils.isEmpty(entity.getImgUrl());
                }).collect(Collectors.toList());
                //5.2）、sku的图片信息；pms_sku_image
                skuImagesService.saveBatch(imagesEntities);
                //TODO 没有图片路径的无需保存

                List<AttrsVo> attrsVo = item.getAttr();
                List<SkuSaleAttrValue> skuSaleAttrValueEntities = attrsVo.stream().map(a -> {
                    SkuSaleAttrValue skuSaleAttrValue = new SkuSaleAttrValue();
                    BeanUtils.copyProperties(a, skuSaleAttrValue);
                    skuSaleAttrValue.setSkuId(skuId);

                    return skuSaleAttrValue;
                }).collect(Collectors.toList());
                //5.3）、sku的销售属性信息：pms_sku_sale_attr_value
                skuSaleAttrValueService.saveBatch(skuSaleAttrValueEntities);

                // //5.4）、sku的优惠、满减等信息；gulimall_sms->sms_sku_ladder\sms_sku_full_reduction\sms_member_price
                SkuReductionTo skuReductionTo = new SkuReductionTo();
                BeanUtils.copyProperties(item, skuReductionTo);
                skuReductionTo.setSkuId(skuId);
                if (skuReductionTo.getFullCount() > 0 || skuReductionTo.getFullPrice().compareTo(new BigDecimal("0")) == 1) {
                    R r1 = operationFeignService.saveSkuReduction(skuReductionTo);
                    if (r1.getCode() != 0) {
                        try {
                            throw new Exception("远程保存sku优惠信息失败");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        log.error("远程保存sku优惠信息失败");
                    }
                }


            });
        }

    }

    @Override
    public void saveBaseSpuInfo(SpuInfo info) {
        this.baseMapper.insert(info);
    }


}