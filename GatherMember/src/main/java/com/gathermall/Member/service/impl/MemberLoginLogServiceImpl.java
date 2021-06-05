package com.gathermall.Member.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.common.utils.Query;

import com.gathermall.Member.dao.MemberLoginLogDao;
import com.gathermall.Member.entity.MemberLoginLog;
import com.gathermall.Member.service.MemberLoginLogService;


@Service
public class MemberLoginLogServiceImpl extends ServiceImpl<MemberLoginLogDao, MemberLoginLog> implements MemberLoginLogService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MemberLoginLog> page = this.page(
                new Query<MemberLoginLog>().getPage(params),
                new QueryWrapper<MemberLoginLog>()
        );

        return new PageUtils(page);
    }

}