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
}

