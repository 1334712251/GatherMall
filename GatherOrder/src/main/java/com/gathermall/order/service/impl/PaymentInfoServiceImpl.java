package com.gathermall.order.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.common.utils.Query;

import com.gathermall.order.dao.PaymentInfoDao;
import com.gathermall.order.entity.PaymentInfo;
import com.gathermall.order.service.PaymentInfoService;


@Service("paymentInfoService")
public class PaymentInfoServiceImpl extends ServiceImpl<PaymentInfoDao, PaymentInfo> implements PaymentInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PaymentInfo> page = this.page(
                new Query<PaymentInfo>().getPage(params),
                new QueryWrapper<PaymentInfo>()
        );

        return new PageUtils(page);
    }

}