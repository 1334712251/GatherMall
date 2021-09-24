package com.gathermall.discounts.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.discounts.entity.Coupon;

import java.util.Map;


public interface CouponService extends IService<Coupon> {

    PageUtils queryPage(Map<String, Object> params);
}

