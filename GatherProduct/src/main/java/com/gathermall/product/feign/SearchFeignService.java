package com.gathermall.product.feign;


import com.gathermall.common.config.ServiceFeignConfiguration;
import com.gathermall.common.to.es.SkuEsModel;
import com.gathermall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


@FeignClient(value = "GatherSearch",configuration = ServiceFeignConfiguration.class)
public interface SearchFeignService {


    @PostMapping("/search/save/product")
    R productStatusUp(@RequestBody List<SkuEsModel> skuEsModels);
}
