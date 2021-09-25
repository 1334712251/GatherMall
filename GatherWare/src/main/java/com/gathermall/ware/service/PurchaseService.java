package com.gathermall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.ware.entity.Purchase;
import com.gathermall.ware.vo.MergeVo;
import com.gathermall.ware.vo.PurchaseDoneVo;

import java.util.List;
import java.util.Map;


public interface PurchaseService extends IService<Purchase> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPageUnreceivePurchase(Map<String, Object> params);

    void mergePurchase(MergeVo mergeVo);

    void received(List<Long> ids);

    void done(PurchaseDoneVo doneVo);
}

