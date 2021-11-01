package com.gathermall.search.vo;

import lombok.Data;

import java.util.List;


/**
 * 封装页面所有可能传递过来的查询条件
 */
@Data
public class SearchParam {

    /**
     * 页面传递过来的全文匹配关键字
     */
    private String keyword;

    /**
     * 品牌id,可以多选
     */
    private List<Long> brandId;

    /**
     * 三级分类id
     */
    private Long catalog3Id;

    /**
     * 排序条件：sort=price/salecount/hotscore_desc/asc
     *         hotscore_desc/asc    split  切割
     *
     */
    private String sort;

    /**
     * 是否显示有货  0/1
     */
    private Integer hasStock;

    /**
     * 价格区间查询   skuPrice=1_500
     *              skuPrice=500_
     *              skuPrice=_500
     */
    private String skuPrice;

    /**
     * 按照属性进行筛选
     */
    private List<String> attrs;

    /**
     * 页码
     */
    private Integer pageNum = 1;

    /**
     * 原生的所有查询条件
     */
    private String queryString;


}
