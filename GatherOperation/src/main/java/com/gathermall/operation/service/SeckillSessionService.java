package com.gathermall.operation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.operation.entity.SeckillSession;

import java.util.Map;


public interface SeckillSessionService extends IService<SeckillSession> {

    PageUtils queryPage(Map<String, Object> params);
}

