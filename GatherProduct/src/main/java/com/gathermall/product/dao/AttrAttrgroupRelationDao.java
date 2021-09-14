package com.gathermall.product.dao;

import com.gathermall.product.entity.AttrAttrgroupRelation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AttrAttrgroupRelationDao extends BaseMapper<AttrAttrgroupRelation> {

    void deleteBatchRelation(@Param("list") List<AttrAttrgroupRelation> list);
}
