package com.gathermall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.product.entity.ProductAttrValue;

import java.util.List;
import java.util.Map;


public interface ProductAttrValueService extends IService<ProductAttrValue> {

    PageUtils queryPage(Map<String, Object> params);

    void saveProductAttr(List<ProductAttrValue> collect);

    List<ProductAttrValue> baseAttrlistforspu(Long spuId);

    void updateSpuAttr(Long spuId, List<ProductAttrValue> entities);
}

