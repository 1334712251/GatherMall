package com.gathermall.ware.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.common.utils.Query;

import com.gathermall.ware.dao.PurchaseDao;
import com.gathermall.ware.entity.Purchase;
import com.gathermall.ware.service.PurchaseService;


@Service("purchaseService")
public class PurchaseServiceImpl extends ServiceImpl<PurchaseDao, Purchase> implements PurchaseService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<Purchase> page = this.page(
                new Query<Purchase>().getPage(params),
                new QueryWrapper<Purchase>()
        );

        return new PageUtils(page);
    }

}