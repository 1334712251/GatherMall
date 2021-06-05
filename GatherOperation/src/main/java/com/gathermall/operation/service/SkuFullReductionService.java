package com.gathermall.operation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.operation.entity.SkuFullReduction;

import java.util.Map;


public interface SkuFullReductionService extends IService<SkuFullReduction> {

    PageUtils queryPage(Map<String, Object> params);
}

