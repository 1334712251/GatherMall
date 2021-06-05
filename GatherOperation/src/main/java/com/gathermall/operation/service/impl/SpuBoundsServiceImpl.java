package com.gathermall.operation.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.common.utils.Query;

import com.gathermall.operation.dao.SpuBoundsDao;
import com.gathermall.operation.entity.SpuBounds;
import com.gathermall.operation.service.SpuBoundsService;


@Service("spuBoundsService")
public class SpuBoundsServiceImpl extends ServiceImpl<SpuBoundsDao, SpuBounds> implements SpuBoundsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SpuBounds> page = this.page(
                new Query<SpuBounds>().getPage(params),
                new QueryWrapper<SpuBounds>()
        );

        return new PageUtils(page);
    }

}