package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.atguigu.base.BaseDao;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.dao.DictDao;
import com.atguigu.entity.Dict;
import com.atguigu.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * title:
 *
 * @Author xu
 * @Date 2022/06/20 16:17
 * @FileName: DictServiceImpl
 */
@Service(interfaceClass = DictService.class)
@Transactional
public class DictServiceImpl extends BaseServiceImpl<Dict> implements DictService {
    @Autowired
    DictDao dictDao;

    @Autowired
    JedisPool jedisPool;

    @Override
    protected BaseDao<Dict> getEntityDao() {
        return dictDao;
    }

    //数据字典 树形结构 ztree 需要维护和修改 不进行redis缓存
    @Override
    public List<Map<String, Object>> findZnodesByParentId(Long parentId) {
        //虽然可以返回泛型为map的集合，但是由于需要业务处理，还是返回泛型为实体类型
        List<Dict> list = dictDao.findZnodesByParentId(parentId);
        //需要进行类型转换
        List<Map<String, Object>> data = new ArrayList<>();
        for (Dict dict : list) {
            //isParent为true表示节点为父节点，有孩子就是父节点，没孩子叫叶子节点
            HashMap<String, Object> map = new HashMap<>();//代表一个节点
            map.put("id", dict.getId());
            map.put("pId", dict.getParentId());
            map.put("name", dict.getName());
            Long pId = dict.getId();
            int count = dictDao.countIsParent(pId);//根据pId查询孩子数量
            map.put("isParent", count > 0 ? true : false);
            data.add(map);
        }
        return data;
    }

    @Override
    public List<Dict> findListByParentId(Long parentId) {
        Jedis jedis = null;
        try {
            //1.先从缓存中查询，如果有数据，将redis里的字符串数据通过JSON工具类转换成List集合，直接返回，无需查询数据库
            jedis = jedisPool.getResource();
            String key = "shf:dict:parentId:" + parentId;
            String value = jedis.get(key);
            if (!StringUtils.isEmpty(value)) {
                Type listType = new TypeReference<List<Dict>>() {
                }.getType();
                List redisList = JSON.parseObject(value, listType);
                return redisList;
            }
            //2.如果缓存中没有，查询数据库，并且将数据存放到缓存中，方便下次直接访问缓存
            List<Dict> dbList = dictDao.findZnodesByParentId(parentId);
            if (!CollectionUtils.isEmpty(dbList)) {
                jedis.set(key, JSON.toJSONString(dbList));
                return dbList;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
                if (jedis.isConnected()) {
                    try {
                        jedis.disconnect();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }

    @Override
    public List<Dict> findListByDictCode(String dictCode) {
        Jedis jedis = null;
        try {
            //1.先从缓存中查询，如果有数据，将redis里的字符串数据通过JSON工具类转换成List集合，直接返回，无需查询数据库
            jedis = jedisPool.getResource();
            String key = "shf:dict:dictCode:" + dictCode;
            String value = jedis.get(key);
            if (!StringUtils.isEmpty(value)) {
                Type listType = new TypeReference<List<Dict>>() {
                }.getType();
                List redisList = JSON.parseObject(value, listType);
                return redisList;
            }
            //2.如果缓存中没有，查询数据库，并且将数据存放到缓存中，方便下次直接访问缓存
            Dict dict = dictDao.findDictByDictCode(dictCode);
            List<Dict> dbList = dictDao.findZnodesByParentId(dict.getId());//拿主键当外键来用
            if (!CollectionUtils.isEmpty(dbList)) {
                jedis.set(key, JSON.toJSONString(dbList));
                return dbList;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
                if (jedis.isConnected()) {
                    try {
                        jedis.disconnect();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }

    @Override
    public String getNameById(Long id) {
        Jedis jedis = null;
        try {
            //1.先从缓存中查询，如果有数据，将redis里的字符串数据通过JSON工具类转换成List集合，直接返回，无需查询数据库
            jedis = jedisPool.getResource();
            String key = "shf:dict:id:" + id;
            String value = jedis.get(key);
            if (!StringUtils.isEmpty(value)) {
                return value;
            }
            //2.如果缓存中没有，查询数据库，并且将数据存放到缓存中，方便下次直接访问缓存
            String name = dictDao.getNameById(id);
            if (!StringUtils.isEmpty(name)) {
                jedis.set(key, name);
                return name;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
                if (jedis.isConnected()) {
                    try {
                        jedis.disconnect();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }
}
