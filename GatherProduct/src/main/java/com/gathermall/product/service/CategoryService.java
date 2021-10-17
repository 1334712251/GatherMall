package com.gathermall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.product.entity.Category;
import com.gathermall.product.vo.Catelog2Vo;

import java.util.List;
import java.util.Map;


public interface CategoryService extends IService<Category> {

    PageUtils queryPage(Map<String, Object> params);

    List<Category> listWithTree();

    /**
     * 找到catelogId的完整路径：
     * [父/子/孙]
     * @param catelogId
     * @return
     */
    Long[] findCatelogPath(Long catelogId);

    void updateCascade(Category category);

    void removeMenuByIds(List<Long> asList);


    //获取1级分类
    List<Category> getLevel1Categorys();


    //获取二级分类和三级分类
    Map<String, List<Catelog2Vo>> getCatalogJson();
}

