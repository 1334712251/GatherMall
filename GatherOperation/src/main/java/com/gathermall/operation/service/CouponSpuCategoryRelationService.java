package com.gathermall.operation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.operation.entity.CouponSpuCategoryRelation;

import java.util.Map;


public interface CouponSpuCategoryRelationService extends IService<CouponSpuCategoryRelation> {

    PageUtils queryPage(Map<String, Object> params);
}

