package com.gathermall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.order.entity.RefundInfo;

import java.util.Map;


public interface RefundInfoService extends IService<RefundInfo> {

    PageUtils queryPage(Map<String, Object> params);
}

