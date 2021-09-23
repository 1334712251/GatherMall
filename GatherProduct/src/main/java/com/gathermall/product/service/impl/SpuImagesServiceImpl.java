package com.gathermall.product.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.common.utils.Query;

import com.gathermall.product.dao.SpuImagesDao;
import com.gathermall.product.entity.SpuImages;
import com.gathermall.product.service.SpuImagesService;
import org.springframework.transaction.annotation.Transactional;


@Service("spuImagesService")
@Transactional
public class SpuImagesServiceImpl extends ServiceImpl<SpuImagesDao, SpuImages> implements SpuImagesService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SpuImages> page = this.page(
                new Query<SpuImages>().getPage(params),
                new QueryWrapper<SpuImages>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveImages(Long id, List<String> images) {
        if(images == null || images.size() == 0){

        }else{
            List<SpuImages> collect = images.stream().map(img -> {
                SpuImages spuImages = new SpuImages();
                spuImages.setSpuId(id);
                spuImages.setImgUrl(img);

                return spuImages;
            }).collect(Collectors.toList());
            this.saveBatch(collect);
        }
    }

}