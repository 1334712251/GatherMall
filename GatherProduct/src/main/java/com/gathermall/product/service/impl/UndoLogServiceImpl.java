package com.gathermall.product.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.common.utils.Query;

import com.gathermall.product.dao.UndoLogDao;
import com.gathermall.product.entity.UndoLog;
import com.gathermall.product.service.UndoLogService;
import org.springframework.transaction.annotation.Transactional;


@Service("undoLogService")
@Transactional
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