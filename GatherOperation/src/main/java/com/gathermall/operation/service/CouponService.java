package com.gathermall.operation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.operation.entity.Coupon;

import java.util.Map;


public interface CouponService extends IService<Coupon> {

    PageUtils queryPage(Map<String, Object> params);
}

