package com.gathermall.order.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.common.utils.Query;

import com.gathermall.order.dao.ItemDao;
import com.gathermall.order.entity.Item;
import com.gathermall.order.service.ItemService;


@Service("itemService")
public class ItemServiceImpl extends ServiceImpl<ItemDao, Item> implements ItemService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<Item> page = this.page(
                new Query<Item>().getPage(params),
                new QueryWrapper<Item>()
        );

        return new PageUtils(page);
    }

}