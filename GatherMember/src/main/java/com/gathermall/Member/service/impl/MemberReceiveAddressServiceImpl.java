package com.gathermall.Member.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.common.utils.Query;

import com.gathermall.Member.dao.MemberReceiveAddressDao;
import com.gathermall.Member.entity.MemberReceiveAddress;
import com.gathermall.Member.service.MemberReceiveAddressService;


@Service
public class MemberReceiveAddressServiceImpl extends ServiceImpl<MemberReceiveAddressDao, MemberReceiveAddress> implements MemberReceiveAddressService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MemberReceiveAddress> page = this.page(
                new Query<MemberReceiveAddress>().getPage(params),
                new QueryWrapper<MemberReceiveAddress>()
        );

        return new PageUtils(page);
    }

}