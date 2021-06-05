package com.gathermall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.product.entity.SkuInfo;

import java.util.Map;


public interface SkuInfoService extends IService<SkuInfo> {

    PageUtils queryPage(Map<String, Object> params);
}

