package com.gathermall.Member.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.common.utils.Query;

import com.gathermall.Member.dao.MemberCollectCommodityDao;
import com.gathermall.Member.entity.MemberCollectCommodity;
import com.gathermall.Member.service.MemberCollectCommodityService;


@Service
public class MemberCollectCommodityServiceImpl extends ServiceImpl<MemberCollectCommodityDao, MemberCollectCommodity> implements MemberCollectCommodityService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MemberCollectCommodity> page = this.page(
                new Query<MemberCollectCommodity>().getPage(params),
                new QueryWrapper<MemberCollectCommodity>()
        );

        return new PageUtils(page);
    }

}