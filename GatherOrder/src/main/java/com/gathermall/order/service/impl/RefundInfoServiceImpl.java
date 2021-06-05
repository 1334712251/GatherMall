package com.gathermall.order.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.common.utils.Query;

import com.gathermall.order.dao.RefundInfoDao;
import com.gathermall.order.entity.RefundInfo;
import com.gathermall.order.service.RefundInfoService;


@Service("refundInfoService")
public class RefundInfoServiceImpl extends ServiceImpl<RefundInfoDao, RefundInfo> implements RefundInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<RefundInfo> page = this.page(
                new Query<RefundInfo>().getPage(params),
                new QueryWrapper<RefundInfo>()
        );

        return new PageUtils(page);
    }

}