package com.gathermall.order.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gathermall.order.entity.Item;
import com.gathermall.order.service.ItemService;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.common.utils.R;


@RestController
@RequestMapping("order/item")
public class ItemController {
    @Autowired
    private ItemService itemService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = itemService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		Item item = itemService.getById(id);

        return R.ok().put("item", item);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody Item item){
		itemService.save(item);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody Item item){
		itemService.updateById(item);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		itemService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
