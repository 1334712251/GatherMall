package com.gathermall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.product.entity.CommentReplay;

import java.util.Map;


public interface CommentReplayService extends IService<CommentReplay> {

    PageUtils queryPage(Map<String, Object> params);
}

