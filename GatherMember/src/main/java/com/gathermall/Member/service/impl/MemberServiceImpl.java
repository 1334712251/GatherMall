package com.gathermall.Member.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.common.utils.Query;

import com.gathermall.Member.dao.MemberDao;
import com.gathermall.Member.entity.Member;
import com.gathermall.Member.service.MemberService;


@Service
public class MemberServiceImpl extends ServiceImpl<MemberDao, Member> implements MemberService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<Member> page = this.page(
                new Query<Member>().getPage(params),
                new QueryWrapper<Member>()
        );

        return new PageUtils(page);
    }

}