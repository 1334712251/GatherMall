package com.gathermall.Member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.Member.entity.MemberCollectCommodity;

import java.util.Map;


public interface MemberCollectCommodityService extends IService<MemberCollectCommodity> {

    PageUtils queryPage(Map<String, Object> params);
}

