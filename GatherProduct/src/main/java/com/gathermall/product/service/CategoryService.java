package com.gathermall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.product.entity.Category;

import java.util.Map;


public interface CategoryService extends IService<Category> {

    PageUtils queryPage(Map<String, Object> params);
}

