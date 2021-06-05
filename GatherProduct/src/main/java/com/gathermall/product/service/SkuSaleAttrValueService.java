package com.gathermall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.product.entity.SkuSaleAttrValue;

import java.util.Map;


public interface SkuSaleAttrValueService extends IService<SkuSaleAttrValue> {

    PageUtils queryPage(Map<String, Object> params);
}

