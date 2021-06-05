package com.gathermall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.product.entity.AttrAttrgroupRelation;

import java.util.Map;


public interface AttrAttrgroupRelationService extends IService<AttrAttrgroupRelation> {

    PageUtils queryPage(Map<String, Object> params);
}

