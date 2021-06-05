package com.gathermall.operation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.operation.entity.HomeSubjectSpu;

import java.util.Map;


public interface HomeSubjectSpuService extends IService<HomeSubjectSpu> {

    PageUtils queryPage(Map<String, Object> params);
}

