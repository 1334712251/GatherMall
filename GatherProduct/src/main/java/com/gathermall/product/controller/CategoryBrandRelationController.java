package com.gathermall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gathermall.product.entity.Brand;
import com.gathermall.product.vo.BrandVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.gathermall.product.entity.CategoryBrandRelation;
import com.gathermall.product.service.CategoryBrandRelationService;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.common.utils.R;


@RestController
@RequestMapping("/product/categorybrandrelation")
public class CategoryBrandRelationController {

    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = categoryBrandRelationService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 1、Controller：处理请求，接受和校验数据
     * 2、Service接受controller传来的数据，进行业务处理
     * 3、Controller接受Service处理完的数据，封装页面指定的vo
     * @param catId
     * @return
     */
    @GetMapping("/brands/list")
    public R relationBrandList(@RequestParam(value = "catId", required = true) Long catId) {
        List<Brand> vos = categoryBrandRelationService.getBrandByCatId(catId);
        List<BrandVo> collect = vos.stream().map(item -> {
            BrandVo brandVo = new BrandVo();
            brandVo.setBrandId(item.getBrandId());
            brandVo.setBrandName(item.getName());
            return brandVo;
        }).collect(Collectors.toList());
        return R.ok().put("data", collect);
    }


    /**
     * 获取品牌关联的分类
     *
     * @param brandId
     * @return
     */
    @GetMapping("/catelog/list")
    public R categoryList(@RequestParam("brandId") Long brandId) {
        List<CategoryBrandRelation> data = categoryBrandRelationService.list(
                new QueryWrapper<CategoryBrandRelation>().eq("brand_id", brandId));

        return R.ok().put("data", data);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        CategoryBrandRelation categoryBrandRelation = categoryBrandRelationService.getById(id);

        return R.ok().put("categoryBrandRelation", categoryBrandRelation);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody CategoryBrandRelation categoryBrandRelation) {
        categoryBrandRelationService.saveDetail(categoryBrandRelation);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody CategoryBrandRelation categoryBrandRelation) {
        categoryBrandRelationService.updateById(categoryBrandRelation);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        categoryBrandRelationService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
