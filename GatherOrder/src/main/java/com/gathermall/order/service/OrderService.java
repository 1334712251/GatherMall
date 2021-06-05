package com.gathermall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.order.entity.Order;

import java.util.Map;


public interface OrderService extends IService<Order> {

    PageUtils queryPage(Map<String, Object> params);
}

