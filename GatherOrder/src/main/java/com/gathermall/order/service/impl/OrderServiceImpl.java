package com.gathermall.order.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.common.utils.Query;

import com.gathermall.order.dao.OrderDao;
import com.gathermall.order.entity.Order;
import com.gathermall.order.service.OrderService;


@Service
public class OrderServiceImpl extends ServiceImpl<OrderDao, Order> implements OrderService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<Order> page = this.page(
                new Query<Order>().getPage(params),
                new QueryWrapper<Order>()
        );

        return new PageUtils(page);
    }

}