package com.gathermall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.ware.entity.WareSku;

import java.util.Map;


public interface WareSkuService extends IService<WareSku> {

    PageUtils queryPage(Map<String, Object> params);
}

