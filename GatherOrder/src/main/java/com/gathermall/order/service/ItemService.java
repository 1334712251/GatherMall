package com.gathermall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.order.entity.Item;

import java.util.Map;


public interface ItemService extends IService<Item> {

    PageUtils queryPage(Map<String, Object> params);
}

