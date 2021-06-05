package com.gathermall.Member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.Member.entity.GrowthChangeHistory;

import java.util.Map;


public interface GrowthChangeHistoryService extends IService<GrowthChangeHistory> {

    PageUtils queryPage(Map<String, Object> params);
}

