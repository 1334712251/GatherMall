package com.gathermall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.product.entity.Attr;
import com.gathermall.product.vo.AttrGroupRelationVo;
import com.gathermall.product.vo.AttrRespVo;
import com.gathermall.product.vo.AttrVo;

import java.util.List;
import java.util.Map;


public interface AttrService extends IService<Attr> {

    PageUtils queryPage(Map<String, Object> params, String attrType);

    void saveAttr(AttrVo attrVo);

    AttrRespVo getAttrInfo(Long attrId);

    void updateAttr(AttrVo attrVo);

    List<Attr> getRelationAttr(Long attrGroupId);

    void deleteRelation(AttrGroupRelationVo[] vos);

    PageUtils getNoRelationAttr(Map<String, Object> params, Long attrGroupId);

    PageUtils queryBaseAttrPage(Map<String, Object> params, Long catelogId, String type);


    /**
     * 在指定的所有属性集合里面，挑出检索属性
     * @param attrIds
     * @return
     */
    List<Long> selectSearchAttrIds(List<Long> attrIds);
}

