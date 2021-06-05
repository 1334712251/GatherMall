package com.gathermall.operation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.operation.entity.SeckillPromotion;

import java.util.Map;


public interface SeckillPromotionService extends IService<SeckillPromotion> {

    PageUtils queryPage(Map<String, Object> params);
}

