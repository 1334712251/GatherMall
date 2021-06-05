package com.gathermall.operation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.operation.entity.SkuLadder;

import java.util.Map;


public interface SkuLadderService extends IService<SkuLadder> {

    PageUtils queryPage(Map<String, Object> params);
}

