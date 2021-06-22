package com.gathermall.product.service.impl;

import com.gathermall.product.service.CategoryBrandRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.common.utils.Query;

import com.gathermall.product.dao.CategoryDao;
import com.gathermall.product.entity.Category;
import com.gathermall.product.service.CategoryService;
import org.springframework.transaction.annotation.Transactional;


@Service("categoryService")
@Transactional
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, Category> implements CategoryService {

    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<Category> page = this.page(
                new Query<Category>().getPage(params),
                new QueryWrapper<Category>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<Category> listWithTree() {
        List<Category> entities = baseMapper.selectList(null);
        List<Category> level1Menus = entities.stream().filter((category) -> {
            return category.getParentCid() == 0;
        }).map((menu)->{
            menu.setChildren(getChildren(menu,entities));
            return menu;
        }).sorted((menu1,menu2)->{
            return (menu2.getSort()==null?0:menu2.getSort()) - (menu1.getSort()==null?0:menu1.getSort());
        }).collect(Collectors.toList());
        return level1Menus;
    }

    @Override
    public Long[] findCatelogPath(Long catelogId) {
        List<Long> paths = new ArrayList<>();
        List<Long> parentPath = findParentPath(catelogId, paths);

        Collections.reverse(parentPath);
        return parentPath.toArray(new Long[parentPath.size()]);
    }

    @Override
    public void updateCascade(Category category) {
        this.updateById(category);
        categoryBrandRelationService.updateCategory(category.getCatId(),category.getName());
    }


    private List<Long> findParentPath(Long catelogId, List<Long> paths){
        paths.add(catelogId);
        Category category = baseMapper.selectById(catelogId);
        if (category.getParentCid()!=0){
            findParentPath(category.getParentCid(),paths);
        }
        return paths;
    }

    private List<Category> getChildren(Category root,List<Category> all){

        List<Category> children = all.stream().filter((category) -> {
            return category.getParentCid() == root.getCatId();
        }).map((category)->{
            category.setChildren(getChildren(category,all));
            return category;
        }).sorted((menu1,menu2)->{
            return (menu2.getSort()==null?0:menu2.getSort()) - (menu1.getSort()==null?0:menu1.getSort());
        }).collect(Collectors.toList());
        return children;
    }
}