package com.gathermall.product.dao;

import com.gathermall.product.entity.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface CategoryDao extends BaseMapper<Category> {
	
}
