package com.gathermall.product.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.common.utils.Query;

import com.gathermall.product.dao.CommentReplayDao;
import com.gathermall.product.entity.CommentReplay;
import com.gathermall.product.service.CommentReplayService;


@Service("commentReplayService")
public class CommentReplayServiceImpl extends ServiceImpl<CommentReplayDao, CommentReplay> implements CommentReplayService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CommentReplay> page = this.page(
                new Query<CommentReplay>().getPage(params),
                new QueryWrapper<CommentReplay>()
        );

        return new PageUtils(page);
    }

}