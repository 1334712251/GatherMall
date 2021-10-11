package com.gathermall.product.dao;

import com.gathermall.product.entity.SpuInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface SpuInfoDao extends BaseMapper<SpuInfo> {

    void updateSpuStatus(@Param("spuId") Long spuId, @Param("code") int code);
}
