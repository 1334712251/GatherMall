package com.gathermall.order.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.common.utils.Query;

import com.gathermall.order.dao.SettingDao;
import com.gathermall.order.entity.Setting;
import com.gathermall.order.service.SettingService;


@Service("settingService")
public class SettingServiceImpl extends ServiceImpl<SettingDao, Setting> implements SettingService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<Setting> page = this.page(
                new Query<Setting>().getPage(params),
                new QueryWrapper<Setting>()
        );

        return new PageUtils(page);
    }

}