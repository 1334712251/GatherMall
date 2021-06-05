package com.gathermall.operation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.operation.entity.HomeAdv;

import java.util.Map;


public interface HomeAdvService extends IService<HomeAdv> {

    PageUtils queryPage(Map<String, Object> params);
}

