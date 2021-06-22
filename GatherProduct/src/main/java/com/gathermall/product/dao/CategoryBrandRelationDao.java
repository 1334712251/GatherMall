package com.gathermall.product.dao;

import com.gathermall.product.entity.CategoryBrandRelation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface CategoryBrandRelationDao extends BaseMapper<CategoryBrandRelation> {

    void updateCategory(@Param("catId") Long catId, @Param("name") String name);
}
