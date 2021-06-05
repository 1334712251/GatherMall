package com.gathermall.ware.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.common.utils.Query;

import com.gathermall.ware.dao.WareOrderTaskDao;
import com.gathermall.ware.entity.WareOrderTask;
import com.gathermall.ware.service.WareOrderTaskService;


@Service("wareOrderTaskService")
public class WareOrderTaskServiceImpl extends ServiceImpl<WareOrderTaskDao, WareOrderTask> implements WareOrderTaskService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<WareOrderTask> page = this.page(
                new Query<WareOrderTask>().getPage(params),
                new QueryWrapper<WareOrderTask>()
        );

        return new PageUtils(page);
    }

}