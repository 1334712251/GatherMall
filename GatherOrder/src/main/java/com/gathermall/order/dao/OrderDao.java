package com.gathermall.order.dao;

import com.gathermall.order.entity.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface OrderDao extends BaseMapper<Order> {
	
}
