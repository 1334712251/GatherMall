package com.gathermall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.product.entity.AttrGroup;
import com.gathermall.product.vo.AttrGroupWithAttrsVo;

import java.util.List;
import java.util.Map;


public interface AttrGroupService extends IService<AttrGroup> {

    PageUtils queryPage(Map<String, Object> params);

    List<AttrGroupWithAttrsVo> getAttrGroupWithAttrsByCatelogId(Long catelogId);
}

