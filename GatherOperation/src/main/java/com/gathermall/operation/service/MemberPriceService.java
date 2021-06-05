package com.gathermall.operation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.operation.entity.MemberPrice;

import java.util.Map;


public interface MemberPriceService extends IService<MemberPrice> {

    PageUtils queryPage(Map<String, Object> params);
}

