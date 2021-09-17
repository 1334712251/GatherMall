package com.gathermall.Member.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gathermall.Member.entity.MemberCollectCommodity;
import com.gathermall.Member.service.MemberCollectCommodityService;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.common.utils.R;


@RestController
@RequestMapping("/member/membercollectcommodity")
public class MemberCollectCommodityController {
    @Autowired
    private MemberCollectCommodityService memberCollectCommodityService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = memberCollectCommodityService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		MemberCollectCommodity memberCollectCommodity = memberCollectCommodityService.getById(id);

        return R.ok().put("memberCollectCommodity", memberCollectCommodity);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody MemberCollectCommodity memberCollectCommodity){
		memberCollectCommodityService.save(memberCollectCommodity);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody MemberCollectCommodity memberCollectCommodity){
		memberCollectCommodityService.updateById(memberCollectCommodity);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		memberCollectCommodityService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
