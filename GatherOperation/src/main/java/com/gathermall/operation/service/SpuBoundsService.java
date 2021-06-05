package com.gathermall.operation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.operation.entity.SpuBounds;

import java.util.Map;


public interface SpuBoundsService extends IService<SpuBounds> {

    PageUtils queryPage(Map<String, Object> params);
}

