package com.gathermall.operation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.operation.entity.CouponSpuRelation;

import java.util.Map;


public interface CouponSpuRelationService extends IService<CouponSpuRelation> {

    PageUtils queryPage(Map<String, Object> params);
}

