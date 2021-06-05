package com.gathermall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.product.entity.SpuInfo;

import java.util.Map;


public interface SpuInfoService extends IService<SpuInfo> {

    PageUtils queryPage(Map<String, Object> params);
}

