package com.gathermall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.ware.entity.WareOrderTaskDetail;

import java.util.Map;


public interface WareOrderTaskDetailService extends IService<WareOrderTaskDetail> {

    PageUtils queryPage(Map<String, Object> params);
}

