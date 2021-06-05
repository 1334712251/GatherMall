package com.gathermall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.product.entity.SpuImages;

import java.util.Map;


public interface SpuImagesService extends IService<SpuImages> {

    PageUtils queryPage(Map<String, Object> params);
}

