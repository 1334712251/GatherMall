package com.gathermall.order.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gathermall.order.entity.ReturnReason;
import com.gathermall.order.service.ReturnReasonService;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.common.utils.R;


@RestController
@RequestMapping("order/returnreason")
public class ReturnReasonController {
    @Autowired
    private ReturnReasonService returnReasonService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = returnReasonService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		ReturnReason returnReason = returnReasonService.getById(id);

        return R.ok().put("returnReason", returnReason);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody ReturnReason returnReason){
		returnReasonService.save(returnReason);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody ReturnReason returnReason){
		returnReasonService.updateById(returnReason);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		returnReasonService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
