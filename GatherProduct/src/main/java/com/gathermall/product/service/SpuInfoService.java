package com.gathermall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.product.entity.SpuInfo;
import com.gathermall.product.vo.SpuSaveVo;

import java.util.Map;


public interface SpuInfoService extends IService<SpuInfo> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 保存spu信息
     * @param vo
     */
    void saveSpuInfo(SpuSaveVo vo);

    void saveBaseSpuInfo(SpuInfo info);

    PageUtils queryPageByCondition(Map<String, Object> params);

    /**
     * 商品上架
     * @param spuId
     */
    void up(Long spuId);
}

