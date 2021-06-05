package com.gathermall.Member.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.common.utils.Query;

import com.gathermall.Member.dao.UndoLogDao;
import com.gathermall.Member.entity.UndoLog;
import com.gathermall.Member.service.UndoLogService;


@Service
public class UndoLogServiceImpl extends ServiceImpl<UndoLogDao, UndoLog> implements UndoLogService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UndoLog> page = this.page(
                new Query<UndoLog>().getPage(params),
                new QueryWrapper<UndoLog>()
        );

        return new PageUtils(page);
    }

}