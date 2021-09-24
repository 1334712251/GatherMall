package com.gathermall.discounts.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.discounts.entity.SkuLadder;

import java.util.Map;


public interface SkuLadderService extends IService<SkuLadder> {

    PageUtils queryPage(Map<String, Object> params);
}

