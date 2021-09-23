package com.gathermall.product.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.common.utils.Query;

import com.gathermall.product.dao.SkuInfoDao;
import com.gathermall.product.entity.SkuInfo;
import com.gathermall.product.service.SkuInfoService;
import org.springframework.transaction.annotation.Transactional;


@Service("skuInfoService")
@Transactional
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoDao, SkuInfo> implements SkuInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuInfo> page = this.page(
                new Query<SkuInfo>().getPage(params),
                new QueryWrapper<SkuInfo>()
        );

        return new PageUtils(page);
    }


    @Override
    public void saveSkuInfo(SkuInfo skuInfo) {
        this.baseMapper.insert(skuInfo);
    }

}