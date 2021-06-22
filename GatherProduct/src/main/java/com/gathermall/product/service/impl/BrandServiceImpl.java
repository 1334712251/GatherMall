package com.gathermall.product.service.impl;

import com.gathermall.product.entity.AttrGroup;
import com.gathermall.product.service.CategoryBrandRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.common.utils.Query;

import com.gathermall.product.dao.BrandDao;
import com.gathermall.product.entity.Brand;
import com.gathermall.product.service.BrandService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Service("brandService")
@Transactional
public class BrandServiceImpl extends ServiceImpl<BrandDao, Brand> implements BrandService {

    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        String key = (String) params.get("key");
        QueryWrapper<Brand> wrapper = new QueryWrapper<Brand>();
        if (!StringUtils.isEmpty(key)){
            wrapper.and((obj)->{
                obj.eq("brand_id",key)
                        .or().like("name",key)
                        .or().like("descript",key);
            });
        }
        IPage<Brand> page = this.page(new Query<Brand>().getPage(params), wrapper);

        return new PageUtils(page);
    }

    @Override
    public void updateDetail(Brand brand) {
        this.updateById(brand);
        if (!StringUtils.isEmpty(brand.getName())){
            categoryBrandRelationService.updateBrand(brand.getBrandId(),brand.getName());
        }
    }

}