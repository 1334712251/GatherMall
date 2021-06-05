package com.gathermall.Member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.Member.entity.IntegrationChangeHistory;

import java.util.Map;


public interface IntegrationChangeHistoryService extends IService<IntegrationChangeHistory> {

    PageUtils queryPage(Map<String, Object> params);
}

