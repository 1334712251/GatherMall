package com.gathermall.product.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.common.utils.Query;

import com.gathermall.product.dao.SpuInfoDescDao;
import com.gathermall.product.entity.SpuInfoDesc;
import com.gathermall.product.service.SpuInfoDescService;


@Service("spuInfoDescService")
public class SpuInfoDescServiceImpl extends ServiceImpl<SpuInfoDescDao, SpuInfoDesc> implements SpuInfoDescService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SpuInfoDesc> page = this.page(
                new Query<SpuInfoDesc>().getPage(params),
                new QueryWrapper<SpuInfoDesc>()
        );

        return new PageUtils(page);
    }

}