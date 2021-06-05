package com.gathermall.operation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.operation.entity.CouponHistory;

import java.util.Map;


public interface CouponHistoryService extends IService<CouponHistory> {

    PageUtils queryPage(Map<String, Object> params);
}

