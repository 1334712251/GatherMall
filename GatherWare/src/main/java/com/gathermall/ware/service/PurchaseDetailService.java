package com.gathermall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.ware.entity.PurchaseDetail;

import java.util.List;
import java.util.Map;


public interface PurchaseDetailService extends IService<PurchaseDetail> {

    PageUtils queryPage(Map<String, Object> params);

    List<PurchaseDetail> listDetailByPurchaseId(Long id);
}

