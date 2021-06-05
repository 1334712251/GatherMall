package com.gathermall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.order.entity.Setting;

import java.util.Map;


public interface SettingService extends IService<Setting> {

    PageUtils queryPage(Map<String, Object> params);
}

