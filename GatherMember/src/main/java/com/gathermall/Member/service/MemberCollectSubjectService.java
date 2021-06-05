package com.gathermall.Member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.Member.entity.MemberCollectSubject;

import java.util.Map;


public interface MemberCollectSubjectService extends IService<MemberCollectSubject> {

    PageUtils queryPage(Map<String, Object> params);
}

