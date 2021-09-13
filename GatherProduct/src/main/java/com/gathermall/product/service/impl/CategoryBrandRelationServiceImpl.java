package com.gathermall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.gathermall.product.dao.BrandDao;
import com.gathermall.product.dao.CategoryDao;
import com.gathermall.product.entity.Brand;
import com.gathermall.product.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.common.utils.Query;

import com.gathermall.product.dao.CategoryBrandRelationDao;
import com.gathermall.product.entity.CategoryBrandRelation;
import com.gathermall.product.service.CategoryBrandRelationService;
import org.springframework.transaction.annotation.Transactional;


@Service("categoryBrandRelationService")
@Transactional
public class CategoryBrandRelationServiceImpl extends ServiceImpl<CategoryBrandRelationDao, CategoryBrandRelation> implements CategoryBrandRelationService {

    @Autowired
    private BrandDao brandDao;

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryBrandRelation> page = this.page(
                new Query<CategoryBrandRelation>().getPage(params),
                new QueryWrapper<CategoryBrandRelation>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveDetail(CategoryBrandRelation categoryBrandRelation) {
        Long brandId = categoryBrandRelation.getBrandId();
        Long catelogId = categoryBrandRelation.getCatelogId();

        Brand brand = brandDao.selectById(brandId);
        Category category = categoryDao.selectById(catelogId);

        categoryBrandRelation.setBrandName(brand.getName());
        categoryBrandRelation.setCatelogName(category.getName());

        this.save(categoryBrandRelation);

    }

    /**
     * 通过主键id修改自己其中字段的值
     * @param brandId
     * @param name
     */
    @Override
    public void updateBrand(Long brandId, String name) {
        CategoryBrandRelation categoryBrandRelation = new CategoryBrandRelation();

        categoryBrandRelation.setBrandId(brandId);
        categoryBrandRelation.setBrandName(name);
        this.update(categoryBrandRelation, new UpdateWrapper<CategoryBrandRelation>().eq("brand_id",brandId));
    }


    @Override
    public void updateCategory(Long catId, String name) {
        this.baseMapper.updateCategory(catId,name);
    }

}