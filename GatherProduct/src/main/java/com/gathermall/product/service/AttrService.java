package com.gathermall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.product.entity.Attr;

import java.util.Map;


public interface AttrService extends IService<Attr> {

    PageUtils queryPage(Map<String, Object> params);
}

