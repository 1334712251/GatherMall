package com.gathermall.Member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.Member.entity.MemberLoginLog;

import java.util.Map;


public interface MemberLoginLogService extends IService<MemberLoginLog> {

    PageUtils queryPage(Map<String, Object> params);
}

