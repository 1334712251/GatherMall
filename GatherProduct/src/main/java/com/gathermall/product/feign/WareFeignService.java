package com.gathermall.product.feign;

import com.gathermall.common.to.SkuHasStockVo;
import com.gathermall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("GatherWare")
public interface WareFeignService {


    @PostMapping("/ware/waresku/hasStock")
    R<List<SkuHasStockVo>> getSkuHasStock(@RequestBody List<Long> skuIds);
}
