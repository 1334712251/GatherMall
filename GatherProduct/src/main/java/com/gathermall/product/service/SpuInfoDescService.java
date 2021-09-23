package com.gathermall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.product.entity.SpuInfoDesc;

import java.util.Map;


public interface SpuInfoDescService extends IService<SpuInfoDesc> {

    PageUtils queryPage(Map<String, Object> params);

    void saveSpuInfoDesc(SpuInfoDesc descEntity);
}

