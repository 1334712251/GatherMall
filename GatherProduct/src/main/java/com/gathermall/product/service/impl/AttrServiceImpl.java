package com.gathermall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.gathermall.product.dao.AttrAttrgroupRelationDao;
import com.gathermall.product.dao.AttrGroupDao;
import com.gathermall.product.dao.CategoryDao;
import com.gathermall.product.entity.*;
import com.gathermall.product.service.CategoryService;
import com.gathermall.product.vo.AttrRespVo;
import com.gathermall.product.vo.AttrVo;
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

import com.gathermall.product.dao.AttrDao;
import com.gathermall.product.service.AttrService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Service("attrService")
@Transactional
public class AttrServiceImpl extends ServiceImpl<AttrDao, Attr> implements AttrService {

    @Autowired
    private AttrAttrgroupRelationDao attrAttrgroupRelationDao;

    @Autowired
    private AttrGroupDao attrGroupDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private CategoryService categoryService;

    /**
     *
     * @param params
     * @return
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params, String attrType) {
        //id和属性名、可选值条件查询
        QueryWrapper<Attr> wrapper = new QueryWrapper<Attr>()
                .eq("attr_type", "base".equalsIgnoreCase(attrType) ? 1 : 0);
        String key = (String) params.get("key");
        if (!StringUtils.isEmpty(key)) {
            wrapper.and((obj) -> {
                obj.eq("attr_id", key)
                        .or().like("attr_name", key)
                        .or().like("value_select", key);
            });
        }
        //没有传分类id
        if (params.get("categoryId").equals(0L)) {
            IPage<Attr> page = this.page(new Query<Attr>().getPage(params), wrapper);

            PageUtils pageUtils = new PageUtils(page);
            List<AttrRespVo> respVos = getAttrRespVos(attrType, page);

            pageUtils.setList(respVos);
            return pageUtils;

        } else {
            wrapper.eq("catelog_id", params.get("categoryId"));

            IPage<Attr> page = this.page(new Query<Attr>().getPage(params), wrapper);

            PageUtils pageUtils = new PageUtils(page);
            List<AttrRespVo> respVos = getAttrRespVos(attrType, page);

            pageUtils.setList(respVos);
            return pageUtils;
        }
    }

    /**
     * TODO 优化：把分类和分组信息放在redis里面
     *
     * 匹配分类名字和分组名字
     * @param attrType
     * @param page
     * @return
     */
    private List<AttrRespVo> getAttrRespVos(String attrType, IPage<Attr> page) {
        List<Attr> records = page.getRecords();

        return records.stream().map(attr -> {
            AttrRespVo attrRespVo = new AttrRespVo();
            BeanUtils.copyProperties(attr, attrRespVo);

            if ("base".equalsIgnoreCase(attrType)) {
                AttrAttrgroupRelation attrId = attrAttrgroupRelationDao
                        .selectOne(new QueryWrapper<AttrAttrgroupRelation>()
                                .eq("attr_id", attr.getAttrId()));
                if (!StringUtils.isEmpty(attrId)) {
                    AttrGroup attrGroup = attrGroupDao.selectById(attrId.getAttrGroupId());
                    attrRespVo.setGroupName(attrGroup.getAttrGroupName());
                }
            }

            Category category = categoryDao.selectById(attr.getCatelogId());

            if (!StringUtils.isEmpty(category)) {
                attrRespVo.setCatelogName(category.getName());
            }

            return attrRespVo;
        }).collect(Collectors.toList());
    }

    @Override
    public void saveAttr(AttrVo attrVo) {
        Attr attr = new Attr();
        BeanUtils.copyProperties(attrVo, attr);   //复制属性，从attrVo复制到attr里面
        this.save(attr);
        //保存关联关系
        AttrAttrgroupRelation attrAttrgroupRelation = new AttrAttrgroupRelation();
        attrAttrgroupRelation.setAttrGroupId(attrVo.getAttrGroupId());
        attrAttrgroupRelation.setAttrId(attr.getAttrId());
        attrAttrgroupRelationDao.insert(attrAttrgroupRelation);
    }

    @Override
    public AttrRespVo getAttrInfo(Long attrId) {
        AttrRespVo respVo = new AttrRespVo();
        Attr attr = this.getById(attrId);
        BeanUtils.copyProperties(attr, respVo);

        //设置分组信息
        AttrAttrgroupRelation attrAttrgroupRelation = attrAttrgroupRelationDao.selectOne(new QueryWrapper<AttrAttrgroupRelation>()
                .eq("attr_id", attrId));
        if (!StringUtils.isEmpty(attrAttrgroupRelation)) {
            respVo.setAttrGroupId(attrAttrgroupRelation.getAttrGroupId());
            AttrGroup attrGroup = attrGroupDao.selectById(attrAttrgroupRelation.getAttrGroupId());
            if (attrGroup != null) {
                respVo.setGroupName(attrGroup.getAttrGroupName());
            }
        }
        //设置分类信息
        Long catelogId = attr.getCatelogId();
        Long[] catelogPath = categoryService.findCatelogPath(catelogId);
        respVo.setCatelogPath(catelogPath);
        Category category = categoryDao.selectById(catelogId);
        if (category != null) {
            respVo.setCatelogName(category.getName());
        }

        return respVo;
    }

    @Override
    public void updateAttr(AttrVo attrVo) {
        Attr attr = new Attr();
        BeanUtils.copyProperties(attrVo, attr);
        this.updateById(attr);

        Integer count = attrAttrgroupRelationDao.selectCount(new QueryWrapper<AttrAttrgroupRelation>()
                .eq("attr_id", attrVo.getAttrId()));

        //修改分组关联
        AttrAttrgroupRelation attrAttrgroupRelation = new AttrAttrgroupRelation();
        attrAttrgroupRelation.setAttrGroupId(attrVo.getAttrGroupId());
        attrAttrgroupRelation.setAttrId(attrVo.getAttrId());
        if (count > 0) {
            attrAttrgroupRelationDao.update(attrAttrgroupRelation, new UpdateWrapper<AttrAttrgroupRelation>()
                    .eq("attr_id", attrVo.getAttrId()));
        } else {
            attrAttrgroupRelationDao.insert(attrAttrgroupRelation);
        }
    }

}