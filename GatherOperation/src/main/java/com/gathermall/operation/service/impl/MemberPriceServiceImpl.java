package com.gathermall.operation.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.common.utils.Query;

import com.gathermall.operation.dao.MemberPriceDao;
import com.gathermall.operation.entity.MemberPrice;
import com.gathermall.operation.service.MemberPriceService;


@Service("memberPriceService")
public class MemberPriceServiceImpl extends ServiceImpl<MemberPriceDao, MemberPrice> implements MemberPriceService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MemberPrice> page = this.page(
                new Query<MemberPrice>().getPage(params),
                new QueryWrapper<MemberPrice>()
        );

        return new PageUtils(page);
    }

}