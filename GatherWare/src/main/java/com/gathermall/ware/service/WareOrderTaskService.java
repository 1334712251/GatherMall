package com.gathermall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.ware.entity.WareOrderTask;

import java.util.Map;


public interface WareOrderTaskService extends IService<WareOrderTask> {

    PageUtils queryPage(Map<String, Object> params);
}

