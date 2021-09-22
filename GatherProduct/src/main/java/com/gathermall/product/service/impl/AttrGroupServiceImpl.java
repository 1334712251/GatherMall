package com.gathermall.product.service.impl;

import com.gathermall.product.entity.Attr;
import com.gathermall.product.service.AttrService;
import com.gathermall.product.vo.AttrGroupWithAttrsVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.common.utils.Query;

import com.gathermall.product.dao.AttrGroupDao;
import com.gathermall.product.entity.AttrGroup;
import com.gathermall.product.service.AttrGroupService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Slf4j
@Service("attrGroupService")
@Transactional
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroup> implements AttrGroupService {

    @Autowired
    private AttrService attrService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        QueryWrapper<AttrGroup> wrapper = new QueryWrapper<AttrGroup>();
        String key = (String) params.get("key");
        if (!StringUtils.isEmpty(key)) {
            wrapper.and((obj) -> {
                obj.eq("attr_group_id", key)
                        .or().like("attr_group_name", key)
                        .or().like("descript", key);
            });
        }

        if (StringUtils.isEmpty(params.get("categoryId"))) {

            IPage<AttrGroup> page = this.page(new Query<AttrGroup>().getPage(params), wrapper);
            return new PageUtils(page);
        } else {
            wrapper.eq("catelog_id", params.get("categoryId"));

            IPage<AttrGroup> page = this.page(new Query<AttrGroup>().getPage(params), wrapper);
            return new PageUtils(page);
        }
    }

    /**
     * 根据分类id查出所有的分组以及这些组里面的属性
     * @param catelogId
     * @return
     */
    @Override
    public List<AttrGroupWithAttrsVo> getAttrGroupWithAttrsByCatelogId(Long catelogId) {
        //com.atguigu.gulimall.product.vo
        //1、查询分组信息
        List<AttrGroup> attrGroupEntities = this.list(new QueryWrapper<AttrGroup>().eq("catelog_id", catelogId));

        //2、查询所有属性
        List<AttrGroupWithAttrsVo> collect = attrGroupEntities.stream().map(group -> {
            AttrGroupWithAttrsVo attrsVo = new AttrGroupWithAttrsVo();
            BeanUtils.copyProperties(group,attrsVo);
            List<Attr> attrs = attrService.getRelationAttr(attrsVo.getAttrGroupId());
            attrsVo.setAttrs(attrs);
            return attrsVo;
        }).collect(Collectors.toList());

        return collect;


    }
}