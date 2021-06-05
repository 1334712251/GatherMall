package com.gathermall.operation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.operation.entity.SeckillSkuRelation;

import java.util.Map;


public interface SeckillSkuRelationService extends IService<SeckillSkuRelation> {

    PageUtils queryPage(Map<String, Object> params);
}

