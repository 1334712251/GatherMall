package com.gathermall.Member.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.common.utils.Query;

import com.gathermall.Member.dao.MemberCollectSubjectDao;
import com.gathermall.Member.entity.MemberCollectSubject;
import com.gathermall.Member.service.MemberCollectSubjectService;


@Service
public class MemberCollectSubjectServiceImpl extends ServiceImpl<MemberCollectSubjectDao, MemberCollectSubject> implements MemberCollectSubjectService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MemberCollectSubject> page = this.page(
                new Query<MemberCollectSubject>().getPage(params),
                new QueryWrapper<MemberCollectSubject>()
        );

        return new PageUtils(page);
    }

}