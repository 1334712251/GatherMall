package com.gathermall.operation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.operation.entity.HomeSubject;

import java.util.Map;


public interface HomeSubjectService extends IService<HomeSubject> {

    PageUtils queryPage(Map<String, Object> params);
}

