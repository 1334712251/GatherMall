package com.gathermall.Member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.Member.entity.MemberLevel;

import java.util.Map;


public interface MemberLevelService extends IService<MemberLevel> {

    PageUtils queryPage(Map<String, Object> params);
}

