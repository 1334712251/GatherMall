package com.gathermall.Member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.Member.entity.MemberReceiveAddress;

import java.util.Map;


public interface MemberReceiveAddressService extends IService<MemberReceiveAddress> {

    PageUtils queryPage(Map<String, Object> params);
}

