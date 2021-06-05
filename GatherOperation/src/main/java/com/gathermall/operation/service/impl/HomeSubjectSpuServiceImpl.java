package com.gathermall.operation.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.common.utils.Query;

import com.gathermall.operation.dao.HomeSubjectSpuDao;
import com.gathermall.operation.entity.HomeSubjectSpu;
import com.gathermall.operation.service.HomeSubjectSpuService;


@Service("homeSubjectSpuService")
public class HomeSubjectSpuServiceImpl extends ServiceImpl<HomeSubjectSpuDao, HomeSubjectSpu> implements HomeSubjectSpuService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<HomeSubjectSpu> page = this.page(
                new Query<HomeSubjectSpu>().getPage(params),
                new QueryWrapper<HomeSubjectSpu>()
        );

        return new PageUtils(page);
    }

}