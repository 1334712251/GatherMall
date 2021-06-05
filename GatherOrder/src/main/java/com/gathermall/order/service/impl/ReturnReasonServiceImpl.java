package com.gathermall.order.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.common.utils.Query;

import com.gathermall.order.dao.ReturnReasonDao;
import com.gathermall.order.entity.ReturnReason;
import com.gathermall.order.service.ReturnReasonService;


@Service("returnReasonService")
public class ReturnReasonServiceImpl extends ServiceImpl<ReturnReasonDao, ReturnReason> implements ReturnReasonService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ReturnReason> page = this.page(
                new Query<ReturnReason>().getPage(params),
                new QueryWrapper<ReturnReason>()
        );

        return new PageUtils(page);
    }

}