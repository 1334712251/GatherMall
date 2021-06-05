package com.gathermall.Member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.Member.entity.UndoLog;

import java.util.Map;


public interface UndoLogService extends IService<UndoLog> {

    PageUtils queryPage(Map<String, Object> params);
}

