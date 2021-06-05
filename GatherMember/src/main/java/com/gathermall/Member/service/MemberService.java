package com.gathermall.Member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.Member.entity.Member;

import java.util.Map;


public interface MemberService extends IService<Member> {

    PageUtils queryPage(Map<String, Object> params);
}

