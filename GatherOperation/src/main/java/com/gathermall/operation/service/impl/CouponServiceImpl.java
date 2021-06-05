package com.gathermall.operation.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.common.utils.Query;

import com.gathermall.operation.dao.CouponDao;
import com.gathermall.operation.entity.Coupon;
import com.gathermall.operation.service.CouponService;


@Service("couponService")
public class CouponServiceImpl extends ServiceImpl<CouponDao, Coupon> implements CouponService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<Coupon> page = this.page(
                new Query<Coupon>().getPage(params),
                new QueryWrapper<Coupon>()
        );

        return new PageUtils(page);
    }

}