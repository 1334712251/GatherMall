package com.gathermall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.product.entity.CategoryBrandRelation;

import java.util.Map;


public interface CategoryBrandRelationService extends IService<CategoryBrandRelation> {

    PageUtils queryPage(Map<String, Object> params);
}

