package com.gathermall.discounts.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.discounts.entity.CouponSpuCategoryRelation;

import java.util.Map;


public interface CouponSpuCategoryRelationService extends IService<CouponSpuCategoryRelation> {

    PageUtils queryPage(Map<String, Object> params);
}

