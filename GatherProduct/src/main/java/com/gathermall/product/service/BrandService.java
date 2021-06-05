package com.gathermall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.product.entity.Brand;

import java.util.Map;


public interface BrandService extends IService<Brand> {

    PageUtils queryPage(Map<String, Object> params);
}

