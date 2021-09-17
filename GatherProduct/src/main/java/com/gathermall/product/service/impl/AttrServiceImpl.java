package com.gathermall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.gathermall.common.constant.ProductConstant;
import com.gathermall.product.dao.AttrAttrgroupRelationDao;
import com.gathermall.product.dao.AttrGroupDao;
import com.gathermall.product.dao.CategoryDao;
import com.gathermall.product.entity.*;
import com.gathermall.product.service.CategoryService;
import com.gathermall.product.vo.AttrGroupRelationVo;
import com.gathermall.product.vo.AttrRespVo;
import com.gathermall.product.vo.AttrVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
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
     * @param params
     * @return
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params, String attrType) {
        //id和属性名、可选值条件查询
        QueryWrapper<Attr> wrapper = new QueryWrapper<Attr>()
                .eq("attr_type", "base".equalsIgnoreCase(attrType) ? ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode() : ProductConstant.AttrEnum.ATTR_TYPE_SALE.getCode());
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
     * <p>
     * 匹配分类名字和分组名字
     *
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
                if ((!StringUtils.isEmpty(attrId)) && (!StringUtils.isEmpty(attrId.getAttrGroupId()))) {
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
        if (attrVo.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode() && !StringUtils.isEmpty(attr.getAttrGroupId())) {
            AttrAttrgroupRelation attrAttrgroupRelation = new AttrAttrgroupRelation();
            attrAttrgroupRelation.setAttrGroupId(attrVo.getAttrGroupId());
            attrAttrgroupRelation.setAttrId(attrVo.getAttrId());
            attrAttrgroupRelationDao.insert(attrAttrgroupRelation);
        }
    }

    @Override
    public AttrRespVo getAttrInfo(Long attrId) {
        AttrRespVo respVo = new AttrRespVo();
        Attr attr = this.getById(attrId);
        BeanUtils.copyProperties(attr, respVo);

        if (attr.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()) {
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

    /**
     * 更新时要指明更新的类型
     *
     * @param attrVo
     */
    @Override
    public void updateAttr(AttrVo attrVo) {
        Attr attr = new Attr();
        BeanUtils.copyProperties(attrVo, attr);
        this.updateById(attr);

        if (attr.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()) {
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

    /**
     * 根据分组id查找关联的所有基本属性
     *
     * @param attrGroupId
     * @return
     */
    @Override
    public List<Attr> getRelationAttr(Long attrGroupId) {
        List<AttrAttrgroupRelation> list = attrAttrgroupRelationDao.selectList(new QueryWrapper<AttrAttrgroupRelation>().eq("attr_group_id", attrGroupId));

        List<Long> attrIds = list.stream().map((attrAttrgroupRelation) -> attrAttrgroupRelation.getAttrId()).collect(Collectors.toList());

        if (attrIds == null || attrIds.size() == 0) {
            return null;
        }
        List<Attr> attrList = this.listByIds(attrIds);
        return attrList;
    }

    @Override
    public void deleteRelation(AttrGroupRelationVo[] vos) {
        List<AttrAttrgroupRelation> list = Arrays.asList(vos).stream().map((item) -> {
            AttrAttrgroupRelation attrAttrgroupRelation = new AttrAttrgroupRelation();
            BeanUtils.copyProperties(item, attrAttrgroupRelation);
            return attrAttrgroupRelation;
        }).collect(Collectors.toList());
        //批量删除
        attrAttrgroupRelationDao.deleteBatchRelation(list);
    }


    /**
     * TODO 待测
     * <p>
     * 获取当前分组没有关联的所有属性
     *
     * @param params
     * @param attrGroupId
     * @return
     */
    @Override
    public PageUtils getNoRelationAttr(Map<String, Object> params, Long attrGroupId) {
        //当前分组只能关联自己所属的分类里面的所有属性
        AttrGroup attrGroup = attrGroupDao.selectById(attrGroupId);
        Long catelogId = attrGroup.getCatelogId();
        //当前分组只能只能关联别的分组没有引用的属性
        //2-1  当前分类下的其它分组
        List<AttrGroup> groupList = attrGroupDao.selectList(new QueryWrapper<AttrGroup>().eq("catelog_id", catelogId));
        List<Long> collect = groupList.stream().map(item -> {
            return item.getAttrGroupId();
        }).collect(Collectors.toList());
        //2-2  这些分组关联的属性
        List<AttrAttrgroupRelation> groupId = attrAttrgroupRelationDao.selectList(new QueryWrapper<AttrAttrgroupRelation>().in("attr_group_id", collect));
        List<Long> attrIds = groupId.stream().map(item -> {
            return item.getAttrId();
        }).collect(Collectors.toList());
        //2-3  从当前分类的所有属性中移除这些属性
        QueryWrapper<Attr> wrapper = new QueryWrapper<Attr>().eq("catelog_id", catelogId).eq("attr_type", ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode());
        if (attrIds != null && attrIds.size() > 0) {
            wrapper.notIn("attr_id", attrIds);
        }
        //条件查询
        String key = (String) params.get("key");
        if (!StringUtils.isEmpty(key)) {
            wrapper.and((w) -> {
                w.eq("attr_id", key).or().like("attr_name", key);
            });
        }
        IPage<Attr> page = this.page(new Query<Attr>().getPage(params), wrapper);

        return new PageUtils(page);
    }
}