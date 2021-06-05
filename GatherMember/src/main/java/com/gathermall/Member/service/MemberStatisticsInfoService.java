package com.gathermall.Member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.Member.entity.MemberStatisticsInfo;

import java.util.Map;


public interface MemberStatisticsInfoService extends IService<MemberStatisticsInfo> {

    PageUtils queryPage(Map<String, Object> params);
}

