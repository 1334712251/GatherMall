package com.gathermall.discounts.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.discounts.entity.UndoLog;

import java.util.Map;


public interface UndoLogService extends IService<UndoLog> {

    PageUtils queryPage(Map<String, Object> params);
}

