package com.gathermall.order.dao;

import com.gathermall.order.entity.Item;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface ItemDao extends BaseMapper<Item> {
	
}
