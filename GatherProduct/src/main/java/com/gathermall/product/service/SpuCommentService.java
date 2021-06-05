package com.gathermall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.product.entity.SpuComment;

import java.util.Map;


public interface SpuCommentService extends IService<SpuComment> {

    PageUtils queryPage(Map<String, Object> params);
}

