package com.gathermall.ware.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.common.utils.Query;

import com.gathermall.ware.dao.PurchaseDetailDao;
import com.gathermall.ware.entity.PurchaseDetail;
import com.gathermall.ware.service.PurchaseDetailService;


@Service("purchaseDetailService")
public class PurchaseDetailServiceImpl extends ServiceImpl<PurchaseDetailDao, PurchaseDetail> implements PurchaseDetailService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PurchaseDetail> page = this.page(
                new Query<PurchaseDetail>().getPage(params),
                new QueryWrapper<PurchaseDetail>()
        );

        return new PageUtils(page);
    }

}