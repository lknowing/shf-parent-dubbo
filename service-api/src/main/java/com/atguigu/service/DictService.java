package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.Dict;

import java.util.List;
import java.util.Map;

/**
 * title:
 *
 * @Author xu
 * @Date 2022/06/20 16:14
 * @FileName: DictService
 */
public interface DictService extends BaseService<Dict> {

    List<Map<String, Object>> findZnodesByParentId(Long parentId);

    List<Dict> findListByParentId(Long parentId);

    List<Dict> findListByDictCode(String dictCode);

    String getNameById(Long id);
}
