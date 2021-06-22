package com.gathermall.product.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.gathermall.product.entity.Category;
import com.gathermall.product.service.CategoryService;
import com.gathermall.common.utils.R;


@RestController
@RequestMapping("product/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 列表
     */
    @GetMapping("/list/tree")
    public R list(){
        List<Category> data = categoryService.listWithTree();
        return R.ok().put("data", data);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{catId}")
    public R info(@PathVariable("catId") Long catId){
		Category category = categoryService.getById(catId);

        return R.ok().put("category", category);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody Category category){
		categoryService.save(category);

        return R.ok();
    }

    @PutMapping("/update/sort")
    public R updateSort(@RequestBody Category[] category){
        categoryService.updateBatchById(Arrays.asList(category));
        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@RequestBody Category category){
		categoryService.updateCascade(category);
        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{catIds}")
    public R delete(@PathVariable Long[] catIds){
		categoryService.removeByIds(Arrays.asList(catIds));

        return R.ok();
    }

}
