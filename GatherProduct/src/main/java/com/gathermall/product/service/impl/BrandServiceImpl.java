package com.gathermall.product.service.impl;

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


@Service("brandService")
public class BrandServiceImpl extends ServiceImpl<BrandDao, Brand> implements BrandService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<Brand> page = this.page(
                new Query<Brand>().getPage(params),
                new QueryWrapper<Brand>()
        );

        return new PageUtils(page);
    }

}