package com.gathermall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.product.entity.Category;

import java.util.List;
import java.util.Map;


public interface CategoryService extends IService<Category> {

    PageUtils queryPage(Map<String, Object> params);

    List<Category> listWithTree();

    Long[] findCatelogPath(Long catelogId);

    void updateCascade(Category category);

    void removeMenuByIds(List<Long> asList);
}

