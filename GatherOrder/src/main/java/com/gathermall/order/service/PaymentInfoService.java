package com.gathermall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.order.entity.PaymentInfo;

import java.util.Map;


public interface PaymentInfoService extends IService<PaymentInfo> {

    PageUtils queryPage(Map<String, Object> params);
}

