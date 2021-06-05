package com.gathermall.Member.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.common.utils.Query;

import com.gathermall.Member.dao.MemberLevelDao;
import com.gathermall.Member.entity.MemberLevel;
import com.gathermall.Member.service.MemberLevelService;


@Service
public class MemberLevelServiceImpl extends ServiceImpl<MemberLevelDao, MemberLevel> implements MemberLevelService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MemberLevel> page = this.page(
                new Query<MemberLevel>().getPage(params),
                new QueryWrapper<MemberLevel>()
        );

        return new PageUtils(page);
    }

}