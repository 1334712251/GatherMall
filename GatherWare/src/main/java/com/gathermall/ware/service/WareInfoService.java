package com.gathermall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.ware.entity.WareInfo;

import java.util.Map;


public interface WareInfoService extends IService<WareInfo> {

    PageUtils queryPage(Map<String, Object> params);
}

