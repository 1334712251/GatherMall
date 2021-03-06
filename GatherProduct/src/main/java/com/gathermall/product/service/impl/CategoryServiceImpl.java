package com.gathermall.product.service.impl;

import com.gathermall.product.entity.CategoryBrandRelation;
import com.gathermall.product.service.CategoryBrandRelationService;
import com.gathermall.product.vo.Catelog2Vo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gathermall.common.utils.PageUtils;
import com.gathermall.common.utils.Query;

import com.gathermall.product.dao.CategoryDao;
import com.gathermall.product.entity.Category;
import com.gathermall.product.service.CategoryService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Service("categoryService")
@Transactional
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, Category> implements CategoryService {

    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;


    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedissonClient redissonClient;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<Category> page = this.page(
                new Query<Category>().getPage(params),
                new QueryWrapper<Category>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<Category> listWithTree() {
        // 1 ??????????????????
        List<Category> entities = baseMapper.selectList(null);
        // 2 ??????????????????????????????
        List<Category> level1Menus = entities.stream().filter((category) -> {
            return category.getParentCid() == 0;
        }).map((menu) -> {
            menu.setChildren(getChildren(menu, entities));
            return menu;
        }).sorted((menu1, menu2) -> {
            return (menu2.getSort() == null ? 0 : menu2.getSort()) - (menu1.getSort() == null ? 0 : menu1.getSort());
        }).collect(Collectors.toList());
        return level1Menus;
    }

    @Override
    public Long[] findCatelogPath(Long catelogId) {
        List<Long> paths = new ArrayList<>();
        //?????????????????????????????????
        List<Long> parentPath = findParentPath(catelogId, paths);
        //????????????????????????
        Collections.reverse(parentPath);
        return parentPath.toArray(new Long[parentPath.size()]);
    }

    //?????????????????????????????????@Caching
    // @Caching(evict = {
    //         @CacheEvict(value = "category",key = "'getLevel1Categorys'"),
    //         @CacheEvict(value = "category",key = "'getCatalogJson'")
    // })
    @CacheEvict(value = "category", allEntries = true)       //????????????????????????????????????
    @Override
    public void updateCascade(Category category) {
        this.updateById(category);
        if (!StringUtils.isEmpty(category.getName())) {
            categoryBrandRelationService.updateCategory(category.getCatId(), category.getName());
        }
    }

    @Override
    public void removeMenuByIds(List<Long> asList) {
        //???????????????????????????id??????????????????????????????
        List<CategoryBrandRelation> categoryBrandRelation = categoryBrandRelationService
                .list(new QueryWrapper<CategoryBrandRelation>()
                        .in("catelog_id", asList));

        if (categoryBrandRelation.size() == 0) {
            //????????????
            baseMapper.deleteBatchIds(asList);
        } else {
            throw new RuntimeException("??????????????????????????????????????????!");
        }
    }


    /**
     * ??????????????????id
     *
     * @param catelogId
     * @param paths
     * @return
     */
    private List<Long> findParentPath(Long catelogId, List<Long> paths) {
        paths.add(catelogId);
        Category category = baseMapper.selectById(catelogId);
        if (category.getParentCid() != 0) {
            findParentPath(category.getParentCid(), paths);
        }
        return paths;
    }

    /**
     * ??????????????????
     *
     * @param root
     * @param all
     * @return
     */
    private List<Category> getChildren(Category root, List<Category> all) {

        List<Category> children = all.stream().filter((category) -> {
            return category.getParentCid() == root.getCatId();
        }).map((category) -> {
            category.setChildren(getChildren(category, all));
            return category;
        }).sorted((menu1, menu2) -> {
            return (menu2.getSort() == null ? 0 : menu2.getSort()) - (menu1.getSort() == null ? 0 : menu1.getSort());
        }).collect(Collectors.toList());
        return children;
    }


    // ???????????????????????????????????????????????????????????????????????????????????????????????????
    // ?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
    @Cacheable(value = {"category"}, key = "#root.method.name", sync = true)
    @Override
    public List<Category> getLevel1Categorys() {
        System.out.println("getLevel1Categorys........");
        long l = System.currentTimeMillis();
        List<Category> categoryEntities = this.baseMapper.selectList(
                new QueryWrapper<Category>().eq("parent_cid", 0));
        System.out.println("???????????????" + (System.currentTimeMillis() - l));
        return categoryEntities;
    }


    @Cacheable(value = "category", key = "#root.methodName")
    @Override
    public Map<String, List<Catelog2Vo>> getCatalogJson() {
        System.out.println("??????????????????");

        //???????????????????????????????????????
        List<Category> selectList = this.baseMapper.selectList(null);

        //1?????????????????????
        //1???1???????????????????????????
        List<Category> level1Categorys = getParent_cid(selectList, 0L);

        //????????????
        Map<String, List<Catelog2Vo>> parentCid = level1Categorys.stream().collect(Collectors.toMap(k -> k.getCatId().toString(), v -> {
            //1???????????????????????????,???????????????????????????????????????
            List<Category> categoryEntities = getParent_cid(selectList, v.getCatId());

            //2????????????????????????
            List<Catelog2Vo> catelog2Vos = null;
            if (categoryEntities != null) {
                catelog2Vos = categoryEntities.stream().map(l2 -> {
                    Catelog2Vo catelog2Vo = new Catelog2Vo(v.getCatId().toString(), null, l2.getCatId().toString(), l2.getName().toString());

                    //1????????????????????????????????????????????????vo
                    List<Category> level3Catelog = getParent_cid(selectList, l2.getCatId());

                    if (level3Catelog != null) {
                        List<Catelog2Vo.Category3Vo> category3Vos = level3Catelog.stream().map(l3 -> {
                            //2????????????????????????
                            Catelog2Vo.Category3Vo category3Vo = new Catelog2Vo.Category3Vo(l2.getCatId().toString(), l3.getCatId().toString(), l3.getName());

                            return category3Vo;
                        }).collect(Collectors.toList());
                        catelog2Vo.setCatalog3List(category3Vos);
                    }

                    return catelog2Vo;
                }).collect(Collectors.toList());
            }

            return catelog2Vos;
        }));

        return parentCid;
    }


    public Map<String, List<Catelog2Vo>> getCatalogJson2() {
        //???????????????json?????????????????????json???????????????????????????????????????

        /**
         * 1?????????????????????????????????????????????
         * 2?????????????????????(????????????)?????????????????????
         * 3????????????????????????????????????
         */

        //1?????????????????????,????????????????????????json?????????
        //JSON??????????????????????????????
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        String catalogJson = ops.get("catalogJson");
        if (StringUtils.isEmpty(catalogJson)) {
            System.out.println("???????????????...???????????????...");
            //2??????????????????????????????????????????
            Map<String, List<Catelog2Vo>> catalogJsonFromDb = getCatalogJsonFromDbWithRedissonLock();

            return catalogJsonFromDb;
        }

        System.out.println("????????????...????????????...");
        //?????????????????????
        Map<String, List<Catelog2Vo>> result = new Gson().fromJson(catalogJson, new TypeToken<Map<String, List<Catelog2Vo>>>() {
        }.getType());
        //Map<String, List<Catelog2Vo>> result = JSON.parseObject(catalogJson,new TypeReference<Map<String, List<Catelog2Vo>>>(){});

        return result;
    }

    public Map<String, List<Catelog2Vo>> getCatalogJsonFromDbWithRedissonLock() {

        //1????????????????????????redis??????
        //??????????????????????????????:?????????????????????????????????11???????????? product-11-lock
        //RLock catalogJsonLock = redissonClient.getLock("catalogJson-lock");
        //????????????
        RReadWriteLock readWriteLock = redissonClient.getReadWriteLock("catalogJson-lock");

        RLock rLock = readWriteLock.readLock();

        Map<String, List<Catelog2Vo>> dataFromDb = null;
        try {
            rLock.lock();
            //????????????...????????????
            dataFromDb = getDataFromDb();
        } finally {
            rLock.unlock();
        }
        //??????redis???????????????????????????????????????
        //????????????????????????????????????=????????? lua????????????
        // String lockValue = stringRedisTemplate.opsForValue().get("lock");
        // if (uuid.equals(lockValue)) {
        //     //?????????????????????
        //     stringRedisTemplate.delete("lock");
        // }

        return dataFromDb;

    }


    private Map<String, List<Catelog2Vo>> getDataFromDb() {
        //?????????????????????????????????????????????????????????????????????????????????????????????
        String catalogJson = stringRedisTemplate.opsForValue().get("catalogJson");
        Gson gson = new Gson();
        if (!StringUtils.isEmpty(catalogJson)) {
            //???????????????????????????

            Map<String, List<Catelog2Vo>> result = gson.fromJson(catalogJson, new TypeToken<Map<String, List<Catelog2Vo>>>() {
            }.getType());

            return result;
        }

        System.out.println("??????????????????");

        /**
         * ???????????????????????????????????????
         */
        List<Category> selectList = this.baseMapper.selectList(null);

        //1?????????????????????
        //1???1???????????????????????????
        List<Category> level1Categorys = getParent_cid(selectList, 0L);

        //????????????
        Map<String, List<Catelog2Vo>> parentCid = level1Categorys.stream().collect(Collectors.toMap(k -> k.getCatId().toString(), v -> {
            //1???????????????????????????,???????????????????????????????????????
            List<Category> categoryEntities = getParent_cid(selectList, v.getCatId());

            //2????????????????????????
            List<Catelog2Vo> catelog2Vos = null;
            if (categoryEntities != null) {
                catelog2Vos = categoryEntities.stream().map(l2 -> {
                    Catelog2Vo catelog2Vo = new Catelog2Vo(v.getCatId().toString(), null, l2.getCatId().toString(), l2.getName().toString());

                    //1????????????????????????????????????????????????vo
                    List<Category> level3Catelog = getParent_cid(selectList, l2.getCatId());

                    if (level3Catelog != null) {
                        List<Catelog2Vo.Category3Vo> category3Vos = level3Catelog.stream().map(l3 -> {
                            //2????????????????????????
                            Catelog2Vo.Category3Vo category3Vo = new Catelog2Vo.Category3Vo(l2.getCatId().toString(), l3.getCatId().toString(), l3.getName());

                            return category3Vo;
                        }).collect(Collectors.toList());
                        catelog2Vo.setCatalog3List(category3Vos);
                    }

                    return catelog2Vo;
                }).collect(Collectors.toList());
            }

            return catelog2Vos;
        }));

        //3?????????????????????????????????,???????????????json
        String valueJson = gson.toJson(parentCid);
        stringRedisTemplate.opsForValue().set("catalogJson", valueJson, 1, TimeUnit.DAYS);

        return parentCid;
    }


    private List<Category> getParent_cid(List<Category> selectList, Long parentCid) {
        List<Category> categoryEntities = selectList.stream().filter(item ->
                item.getParentCid().equals(parentCid)).collect(Collectors.toList());
        return categoryEntities;
        // return this.baseMapper.selectList(
        //         new QueryWrapper<CategoryEntity>().eq("parent_cid", parentCid));
    }

}