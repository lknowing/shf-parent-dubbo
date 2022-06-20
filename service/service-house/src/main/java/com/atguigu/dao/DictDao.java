package com.atguigu.dao;

import com.atguigu.base.BaseDao;
import com.atguigu.entity.Dict;

import java.util.List;

/**
 * title:
 *
 * @Author xu
 * @Date 2022/06/20 16:23
 * @FileName: DictDao
 */
public interface DictDao extends BaseDao<Dict> {
    List<Dict> findZnodesByParentId(Long parentId);

    int countIsParent(Long pId);

    Dict findDictByDictCode(String dictCode);

    String getNameById(Long id);
}
