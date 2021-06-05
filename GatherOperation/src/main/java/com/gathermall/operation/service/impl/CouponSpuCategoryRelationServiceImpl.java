package com.gathermall.operation.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.common.utils.Query;

import com.gathermall.operation.dao.CouponSpuCategoryRelationDao;
import com.gathermall.operation.entity.CouponSpuCategoryRelation;
import com.gathermall.operation.service.CouponSpuCategoryRelationService;


@Service("couponSpuCategoryRelationService")
public class CouponSpuCategoryRelationServiceImpl extends ServiceImpl<CouponSpuCategoryRelationDao, CouponSpuCategoryRelation> implements CouponSpuCategoryRelationService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CouponSpuCategoryRelation> page = this.page(
                new Query<CouponSpuCategoryRelation>().getPage(params),
                new QueryWrapper<CouponSpuCategoryRelation>()
        );

        return new PageUtils(page);
    }

}