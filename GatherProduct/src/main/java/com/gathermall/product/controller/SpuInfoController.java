package com.gathermall.product.controller;

import java.util.Arrays;
import java.util.Map;

import com.gathermall.product.vo.SpuSaveVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.gathermall.product.entity.SpuInfo;
import com.gathermall.product.service.SpuInfoService;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.common.utils.R;




@RestController
@RequestMapping("/product/spuinfo")
public class SpuInfoController {
    @Autowired
    private SpuInfoService spuInfoService;


    @PostMapping("/{spuId}/up")
    public R spuUp(@PathVariable("spuId") Long spuId){

        spuInfoService.up(spuId);

        return R.ok();
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = spuInfoService.queryPageByCondition(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		SpuInfo spuInfo = spuInfoService.getById(id);

        return R.ok().put("spuInfo", spuInfo);
    }

    /**
     * 新增商品
     * @param vo
     * @return
     */
    @RequestMapping("/save")
    public R save(@RequestBody SpuSaveVo vo){
        spuInfoService.saveSpuInfo(vo);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody SpuInfo spuInfo){
		spuInfoService.updateById(spuInfo);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		spuInfoService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
