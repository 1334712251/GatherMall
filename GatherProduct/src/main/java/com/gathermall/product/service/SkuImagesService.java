package com.gathermall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.product.entity.SkuImages;

import java.util.Map;


public interface SkuImagesService extends IService<SkuImages> {

    PageUtils queryPage(Map<String, Object> params);
}

