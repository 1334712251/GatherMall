package com.gathermall.product.dao;

import com.gathermall.product.entity.Attr;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface AttrDao extends BaseMapper<Attr> {


    List<Long> selectBatchAttrIds(@Param("attrIds") List<Long> attrIds);
}
