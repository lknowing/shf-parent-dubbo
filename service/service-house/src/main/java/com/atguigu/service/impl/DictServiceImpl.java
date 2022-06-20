package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.base.BaseDao;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.dao.DictDao;
import com.atguigu.entity.Dict;
import com.atguigu.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    protected BaseDao<Dict> getEntityDao() {
        return dictDao;
    }

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
        return dictDao.findZnodesByParentId(parentId);
    }

    @Override
    public List<Dict> findListByDictCode(String dictCode) {
        Dict dict = dictDao.findDictByDictCode(dictCode);
        return dictDao.findZnodesByParentId(dict.getId());//拿主键当外键来用
    }

    @Override
    public String getNameById(Long id) {
        return dictDao.getNameById(id);
    }
}
