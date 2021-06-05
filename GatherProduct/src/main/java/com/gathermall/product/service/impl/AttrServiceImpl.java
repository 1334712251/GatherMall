package com.gathermall.product.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.common.utils.Query;

import com.gathermall.product.dao.AttrDao;
import com.gathermall.product.entity.Attr;
import com.gathermall.product.service.AttrService;


@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, Attr> implements AttrService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<Attr> page = this.page(
                new Query<Attr>().getPage(params),
                new QueryWrapper<Attr>()
        );

        return new PageUtils(page);
    }

}