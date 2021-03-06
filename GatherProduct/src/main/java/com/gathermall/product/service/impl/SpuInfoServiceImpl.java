package com.gathermall.product.service.impl;

import com.gathermall.common.constant.ProductConstant;
import com.gathermall.common.to.SkuHasStockVo;
import com.gathermall.common.to.SkuReductionTo;
import com.gathermall.common.to.SpuBoundTo;
import com.gathermall.common.to.es.SkuEsModel;
import com.gathermall.common.utils.R;
import com.gathermall.product.entity.*;
import com.gathermall.product.feign.OperationFeignService;
import com.gathermall.product.feign.SearchFeignService;
import com.gathermall.product.feign.WareFeignService;
import com.gathermall.product.service.*;
import com.gathermall.product.vo.*;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
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

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private WareFeignService wareFeignService;

    @Autowired
    private SearchFeignService searchFeignService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SpuInfo> page = this.page(
                new Query<SpuInfo>().getPage(params),
                new QueryWrapper<SpuInfo>()
        );

        return new PageUtils(page);
    }

    /**
     * TODO ??????
     *
     * @param vo
     */
    @Override
    public void saveSpuInfo(SpuSaveVo vo) {

        //1?????????spu???????????? pms_spu_info
        SpuInfo spuInfo = new SpuInfo();
        BeanUtils.copyProperties(vo, spuInfo);
        spuInfo.setCreateTime(new Date());
        spuInfo.setUpdateTime(new Date());
        this.saveBaseSpuInfo(spuInfo);

        //2?????????Spu??????????????? pms_spu_info_desc
        List<String> decript = vo.getDecript();
        SpuInfoDesc descEntity = new SpuInfoDesc();
        descEntity.setSpuId(spuInfo.getId());
        descEntity.setDecript(String.join(",", decript));
        spuInfoDescService.saveSpuInfoDesc(descEntity);


        //3?????????spu???????????? pms_spu_images
        List<String> images = vo.getImages();
        spuImagesService.saveImages(spuInfo.getId(), images);


        //4?????????spu???????????????;pms_product_attr_value
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


        //5?????????spu??????????????????gulimall_sms->sms_spu_bounds
        //?????????????????????(???????????????)
        BoundsVo boundsVo = vo.getBounds();
        SpuBoundTo spuBoundTo = new SpuBoundTo();
        BeanUtils.copyProperties(boundsVo, spuBoundTo);
        spuBoundTo.setSpuId(spuInfo.getId());
        R r = operationFeignService.saveSpuBounds(spuBoundTo);
        if (r.getCode() != 0) {
//            try {
//                throw new Exception("????????????spu??????????????????");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
            log.error("????????????spu??????????????????");
        }


        //5???????????????spu???????????????sku?????????

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
                //5.1??????sku??????????????????pms_sku_info
                skuInfoService.saveSkuInfo(skuInfo);

                Long skuId = skuInfo.getSkuId();

                List<SkuImages> imagesEntities = item.getImages().stream().map(img -> {
                    SkuImages skuImagesEntity = new SkuImages();
                    skuImagesEntity.setSkuId(skuId);
                    skuImagesEntity.setImgUrl(img.getImgUrl());
                    skuImagesEntity.setDefaultImg(img.getDefaultImg());
                    return skuImagesEntity;
                }).filter(entity -> {
                    //?????????????????????????????????
                    //??????true???????????????false????????????
                    return !StringUtils.isEmpty(entity.getImgUrl());
                }).collect(Collectors.toList());
                //5.2??????sku??????????????????pms_sku_image
                skuImagesService.saveBatch(imagesEntities);


                List<AttrsVo> attrsVo = item.getAttr();
                List<SkuSaleAttrValue> skuSaleAttrValueEntities = attrsVo.stream().map(a -> {
                    SkuSaleAttrValue skuSaleAttrValue = new SkuSaleAttrValue();
                    BeanUtils.copyProperties(a, skuSaleAttrValue);
                    skuSaleAttrValue.setSkuId(skuId);

                    return skuSaleAttrValue;
                }).collect(Collectors.toList());
                //5.3??????sku????????????????????????pms_sku_sale_attr_value
                skuSaleAttrValueService.saveBatch(skuSaleAttrValueEntities);

                // //5.4??????sku??????????????????????????????gulimall_sms->sms_sku_ladder\sms_sku_full_reduction\sms_member_price
                SkuReductionTo skuReductionTo = new SkuReductionTo();
                BeanUtils.copyProperties(item, skuReductionTo);
                skuReductionTo.setSkuId(skuId);
                if (skuReductionTo.getFullCount() > 0 || skuReductionTo.getFullPrice().compareTo(new BigDecimal(0)) == 1) {
                    R r1 = operationFeignService.saveSkuReduction(skuReductionTo);
                    if (r1.getCode() != 0) {
//                        try {
//                            throw new Exception("????????????sku??????????????????");
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
                        log.error("????????????sku??????????????????");
                    }
                }


            });
        }

    }

    @Override
    public void saveBaseSpuInfo(SpuInfo info) {
        this.baseMapper.insert(info);
    }

    @Override
    public PageUtils queryPageByCondition(Map<String, Object> params) {

        QueryWrapper<SpuInfo> wrapper = new QueryWrapper<>();

        String key = (String) params.get("key");
        if (!StringUtils.isEmpty(key)) {
            wrapper.and((w) -> {
                w.eq("id", key).or().like("spu_name", key).or().like("spu_description", key);
            });
        }
        // status=1 and (id=1 or spu_name like xxx)
        String status = (String) params.get("status");
        if (!StringUtils.isEmpty(status)) {
            wrapper.eq("publish_status", status);
        }

        String brandId = (String) params.get("brandId");
        if (!StringUtils.isEmpty(brandId) && !"0".equalsIgnoreCase(brandId)) {
            wrapper.eq("brand_id", brandId);
        }

        String catelogId = (String) params.get("catelogId");
        if (!StringUtils.isEmpty(catelogId) && !"0".equalsIgnoreCase(catelogId)) {
            wrapper.eq("catalog_id", catelogId);
        }

        /**
         * status: 2
         * key:
         * brandId: 9
         * catelogId: 225
         */

        IPage<SpuInfo> page = this.page(new Query<SpuInfo>().getPage(params), wrapper);

        return new PageUtils(page);
    }

    @Override
    public void up(Long spuId) {

        //????????????sku???????????????????????????????????????
        List<ProductAttrValue> baseAttrs = productAttrValueService.baseAttrlistforspu(spuId);
        List<Long> attrIds = baseAttrs.stream().map(attr -> {
            return attr.getAttrId();
        }).collect(Collectors.toList());

        List<Long> searchAttrIds = attrService.selectSearchAttrIds(attrIds);

        HashSet<Long> isSet = new HashSet<>(searchAttrIds);

        List<SkuEsModel.Attrs> attrsList = baseAttrs.stream().filter(item -> {
            return isSet.contains(item.getAttrId());
        }).map(item -> {
            SkuEsModel.Attrs attrs = new SkuEsModel.Attrs();
            BeanUtils.copyProperties(item, attrs);
            return attrs;
        }).collect(Collectors.toList());


        //1???????????????spuId???????????????sku????????????????????????
        List<SkuInfo> skuInfoList = skuInfoService.list(new QueryWrapper<SkuInfo>().eq("spu_id", spuId));
        List<Long> skuIds = skuInfoList.stream().map(SkuInfo::getSkuId).collect(Collectors.toList());
        //??????????????????????????????????????????????????????
        Map<Long, Boolean> stockMap = null;
        try {
            R r = wareFeignService.getSkuHasStock(skuIds);
            stockMap = r.getData(new TypeToken<List<SkuHasStockVo>>(){}).stream().collect(Collectors.toMap(SkuHasStockVo::getSkuId, item -> item.getHasStock()));
        } catch (Exception e) {
            log.error("????????????????????????????????????{}", e);
        }
        //2???????????????sku??????
        Map<Long, Boolean> finalStockMap = stockMap;
        List<SkuEsModel> esUpProducts = skuInfoList.stream().map(sku -> {
            //?????????????????????
            SkuEsModel skuEsModel = new SkuEsModel();
            BeanUtils.copyProperties(sku, skuEsModel);
            skuEsModel.setSkuPrice(sku.getPrice());
            skuEsModel.setSkuImg(sku.getSkuDefaultImg());
            //??????skuId????????????????????????
            skuEsModel.setHasStock(finalStockMap == null ? true : finalStockMap.get(sku.getSkuId()));
            //2???TODO ????????????????????????0???
            skuEsModel.setHotScore(0L);
            Brand brand = brandService.getById(skuEsModel.getBrandId());
            skuEsModel.setBrandName(brand.getName());
            skuEsModel.setBrandImg(brand.getLogo());

            Category category = categoryService.getById(skuEsModel.getCatalogId());
            skuEsModel.setCatalogName(category.getName());

            skuEsModel.setAttrs(attrsList);

            return skuEsModel;
        }).collect(Collectors.toList());

        //??????????????????es??????????????????
        R r = searchFeignService.productStatusUp(esUpProducts);
        if (r.getCode() == 0) {
            //??????????????????
            baseMapper.updateSpuStatus(spuId, ProductConstant.StatusEnum.SPU_UP.getCode());
        }else {
            //??????????????????
            //TODO ?????????????????????????????? ???????????????
            //Feign????????????
            /**
             * 1. ????????????????????????????????????json
             * 2. ????????????????????????
             * 3. ????????????????????????
             * 4. ??????????????????????????????
             */
        }

    }


}