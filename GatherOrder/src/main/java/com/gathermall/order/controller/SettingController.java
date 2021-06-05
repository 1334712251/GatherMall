package com.gathermall.order.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gathermall.order.entity.Setting;
import com.gathermall.order.service.SettingService;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.common.utils.R;


@RestController
@RequestMapping("order/setting")
public class SettingController {
    @Autowired
    private SettingService settingService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = settingService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		Setting setting = settingService.getById(id);

        return R.ok().put("setting", setting);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody Setting setting){
		settingService.save(setting);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody Setting setting){
		settingService.updateById(setting);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		settingService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
