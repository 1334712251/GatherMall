package com.gathermall.discounts.service.impl;

import com.gathermall.common.to.MemberPriceVo;
import com.gathermall.common.to.SkuReductionTo;
import com.gathermall.discounts.entity.MemberPrice;
import com.gathermall.discounts.entity.SkuLadder;
import com.gathermall.discounts.service.MemberPriceService;
import com.gathermall.discounts.service.SkuLadderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.common.utils.Query;

import com.gathermall.discounts.dao.SkuFullReductionDao;
import com.gathermall.discounts.entity.SkuFullReduction;
import com.gathermall.discounts.service.SkuFullReductionService;


@Service("skuFullReductionService")
public class SkuFullReductionServiceImpl extends ServiceImpl<SkuFullReductionDao, SkuFullReduction> implements SkuFullReductionService {

    @Autowired
    private SkuLadderService skuLadderService;

    @Autowired
    private MemberPriceService memberPriceService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuFullReduction> page = this.page(
                new Query<SkuFullReduction>().getPage(params),
                new QueryWrapper<SkuFullReduction>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveSkuReduction(SkuReductionTo reductionTo) {
        //1、// //5.4）、sku的优惠、满减等信息；gulimall_sms->sms_sku_ladder\sms_sku_full_reduction\sms_member_price
        //sms_sku_ladder
        SkuLadder skuLadder = new SkuLadder();
        skuLadder.setSkuId(reductionTo.getSkuId());
        skuLadder.setFullCount(reductionTo.getFullCount());
        skuLadder.setDiscount(reductionTo.getDiscount());
        skuLadder.setAddOther(reductionTo.getCountStatus());
        if (reductionTo.getFullCount() > 0) {
            skuLadderService.save(skuLadder);
        }


        //2、sms_sku_full_reduction
        SkuFullReduction reductionEntity = new SkuFullReduction();
        BeanUtils.copyProperties(reductionTo, reductionEntity);
        if (reductionEntity.getFullPrice().compareTo(new BigDecimal("0")) == 1) {
            this.save(reductionEntity);
        }


        //3、sms_member_price
        List<MemberPriceVo> memberPriceVoList = reductionTo.getMemberPrice();

        List<MemberPrice> collect = memberPriceVoList.stream().map(item -> {
            MemberPrice memberPrice = new MemberPrice();
            memberPrice.setSkuId(reductionTo.getSkuId());
            memberPrice.setMemberLevelId(item.getId());
            memberPrice.setMemberLevelName(item.getName());
            memberPrice.setMemberPrice(item.getPrice());
            memberPrice.setAddOther(1);
            return memberPrice;
        }).filter(item -> {
            return item.getMemberPrice().compareTo(new BigDecimal("0")) == 1;
        }).collect(Collectors.toList());

        memberPriceService.saveBatch(collect);
    }

}