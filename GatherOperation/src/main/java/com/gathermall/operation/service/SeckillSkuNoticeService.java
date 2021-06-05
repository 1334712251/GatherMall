package com.gathermall.operation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.operation.entity.SeckillSkuNotice;

import java.util.Map;


public interface SeckillSkuNoticeService extends IService<SeckillSkuNotice> {

    PageUtils queryPage(Map<String, Object> params);
}

