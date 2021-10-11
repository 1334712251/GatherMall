package com.gathermall.search.service;

import com.gathermall.common.to.es.SkuEsModel;

import java.util.List;

public interface ProductSaveService {


    boolean productStatusUp(List<SkuEsModel> skuEsModels) throws Exception;
}
