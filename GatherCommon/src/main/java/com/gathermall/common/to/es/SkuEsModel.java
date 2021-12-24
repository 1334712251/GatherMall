package com.gathermall.common.to.es;


import jdk.internal.util.xml.impl.Attrs;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class SkuEsModel {

    private Long skuId;

    private Long spuId;

    /**
     * 商品标题
     */
    private String skuTitle;

    /**
     * 商品价格
     */
    private BigDecimal skuPrice;

    /**
     * 商品图片
     */
    private String skuImg;

    /**
     * 销量
     */
    private Long saleCount;

    /**
     * 是否有货
     */
    private Boolean hasStock;

    /**
     * 热度评分
     */
    private Long hotScore;

    /**
     * 品牌id
     */
    private Long brandId;

    /**
     * 品牌名字
     */
    private String brandName;

    /**
     * 品牌图片
     */
    private String brandImg;

    /**
     * 分类id
     */
    private Long catalogId;

    /**
     * 分类名字
     */
    private String catalogName;

    /**
     * 商品属性（共性）
     */
    private List<Attrs> attrs;


    /**
     * CPU ： i9，i8,i5
     * 尺寸：1米，两米，三米
     * 。。。。。。
     */
    @Data
    public static class Attrs implements Serializable {

        private Long attrId;

        /**
         * 属性名字
         */
        private String attrName;

        /**
         * 属性数值
         */
        private String attrValue;
    }
}
