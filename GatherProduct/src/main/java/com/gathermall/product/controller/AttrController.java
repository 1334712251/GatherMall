package com.gathermall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.gathermall.product.entity.ProductAttrValue;
import com.gathermall.product.service.ProductAttrValueService;
import com.gathermall.product.vo.AttrRespVo;
import com.gathermall.product.vo.AttrVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import com.gathermall.product.service.AttrService;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.common.utils.R;

@RestController
@RequestMapping("/product/attr")
public class AttrController {

    @Autowired
    private AttrService attrService;

    @Autowired
    private ProductAttrValueService productAttrValueService;


    // /product/attr/base/listforspu/{spuId}
    @GetMapping("/base/listforspu/{spuId}")
    public R baseAttrlistforspu(@PathVariable("spuId") Long spuId){

        List<ProductAttrValue> entities = productAttrValueService.baseAttrlistforspu(spuId);

        return R.ok().put("data",entities);
    }

    //product/attr/sale/list/0?
    ///product/attr/base/list/{catelogId}
    @GetMapping("/{attrType}/list/{catelogId}")
    public R baseAttrList(@RequestParam Map<String, Object> params,
                          @PathVariable("catelogId") Long catelogId,
                          @PathVariable("attrType")String type){

        PageUtils page = attrService.queryBaseAttrPage(params,catelogId,type);
        return R.ok().put("page", page);
    }

    /**
     * 列表
     */
    @RequestMapping("/{attrType}/list/{categoryId}")
    public R list(@RequestParam Map<String, Object> params,
                  @PathVariable("attrType") String attrType,
                  @PathVariable("categoryId")Long categoryId) {
        params.put("categoryId",categoryId);
        PageUtils page = attrService.queryPage(params,attrType);

        return R.ok().put("page", page);
    }


    /**
     * 07、查询属性详情
     */
    @RequestMapping("/info/{attrId}")
    public R info(@PathVariable("attrId") Long attrId) {
        AttrRespVo respVo = attrService.getAttrInfo(attrId);

        return R.ok().put("attr", respVo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody AttrVo attrVo) {
        attrService.saveAttr(attrVo);

        return R.ok();
    }

    /**
     * 08、修改属性
     */
    @RequestMapping("/update")
    public R update(@RequestBody AttrVo attrVo) {
        attrService.updateAttr(attrVo);

        return R.ok();
    }

    ///product/attr/update/{spuId}
    @PostMapping("/update/{spuId}")
    public R updateSpuAttr(@PathVariable("spuId") Long spuId,
                           @RequestBody List<ProductAttrValue> entities){

        productAttrValueService.updateSpuAttr(spuId,entities);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] attrIds) {
        attrService.removeByIds(Arrays.asList(attrIds));

        return R.ok();
    }

}
