package com.gathermall.search.service.impl;

import com.gathermall.common.to.es.SkuEsModel;
import com.gathermall.search.config.ElasticSearchConfig;
import com.gathermall.search.constant.EsConstant;
import com.gathermall.search.service.ProductSaveService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductSaveServiceImpl implements ProductSaveService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Override
    public boolean productStatusUp(List<SkuEsModel> skuEsModels) throws Exception {
        // 保存到es

        // 1 给es中建立索引，product
        BulkRequest bulkRequest = new BulkRequest();
        // 1 构造保存请求
        for (SkuEsModel model : skuEsModels) {
            IndexRequest indexRequest = new IndexRequest(EsConstant.PRODUCT_INDEX);
            indexRequest.id(model.getSkuId().toString());
            String s = new Gson().toJson(model);
            indexRequest.source(s, XContentType.JSON);

            //保存数据
            bulkRequest.add(indexRequest);
        }
        //返回的数据
        BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, ElasticSearchConfig.COMMON_OPTIONS);
        // 判断是否批量发生错误
        boolean b = bulk.hasFailures();
        List<String> collect = Arrays.stream(bulk.getItems()).map(item -> {
            return item.getId();
        }).collect(Collectors.toList());

        log.info("商品上架完成：{},返回数据：{}", collect, bulk.toString());

        return b;
    }
}
