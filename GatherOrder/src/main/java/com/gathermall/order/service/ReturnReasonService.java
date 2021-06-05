package com.gathermall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.order.entity.ReturnReason;

import java.util.Map;


public interface ReturnReasonService extends IService<ReturnReason> {

    PageUtils queryPage(Map<String, Object> params);
}

