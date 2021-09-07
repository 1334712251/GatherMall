package com.gathermall.product.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.common.utils.Query;

import com.gathermall.product.dao.AttrGroupDao;
import com.gathermall.product.entity.AttrGroup;
import com.gathermall.product.service.AttrGroupService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Slf4j
@Service("attrGroupService")
@Transactional
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroup> implements AttrGroupService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        QueryWrapper<AttrGroup> wrapper = new QueryWrapper<AttrGroup>();
        String key = (String) params.get("key");
        if (!StringUtils.isEmpty(key)) {
            wrapper.and((obj) -> {
                obj.eq("attr_group_id", key)
                        .or().like("attr_group_name", key)
                        .or().like("descript", key);
            });
        }

        if (StringUtils.isEmpty(params.get("categoryId"))) {

            IPage<AttrGroup> page = this.page(new Query<AttrGroup>().getPage(params), wrapper);
            return new PageUtils(page);
        } else {
            wrapper.eq("catelog_id", params.get("categoryId"));

            IPage<AttrGroup> page = this.page(new Query<AttrGroup>().getPage(params), wrapper);
            return new PageUtils(page);
        }
    }
}