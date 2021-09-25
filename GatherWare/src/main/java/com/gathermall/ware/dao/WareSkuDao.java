package com.gathermall.ware.dao;

import com.gathermall.ware.entity.WareSku;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface WareSkuDao extends BaseMapper<WareSku> {

    void addStock(Long skuId, Long wareId, Integer skuNum);
}
