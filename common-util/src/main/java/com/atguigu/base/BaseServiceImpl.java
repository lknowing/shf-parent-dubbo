package com.atguigu.base;

import com.atguigu.util.CastUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.io.Serializable;
import java.util.Map;

/**
 * title:
 *
 * @Author xu
 * @Date 2022/06/17 15:47
 * @FileName: BaseServiceImpl
 */
public abstract class BaseServiceImpl<T> implements BaseService<T> {

    protected abstract BaseDao<T> getEntityDao();

    @Override
    public Integer insert(T t) {
        return getEntityDao().insert(t);
    }

    @Override
    public T findById(Serializable id) {
        return getEntityDao().findById(id);
    }

    @Override
    public void update(T t) {
        getEntityDao().update(t);
    }

    @Override
    public PageInfo<T> findPage(Map<String, Object> filters) {
        int pageNum = CastUtil.castInt(filters.get("pageNum"), 1);
        int pageSize = CastUtil.castInt(filters.get("pageSize"), 3);
        //开启分页功能 将这两个参数与当前线程进行绑定，传递给dao层
        // select 语句最后 自动增加 limit ?,?  limit startIndex,pageSize
        // startIndex = (pageNum-1)*pageSize
        PageHelper.startPage(pageNum, pageSize);// ThreadLoca 线程的本地变量 将这两个参数与当前线程进行绑定
        Page<T> page = getEntityDao().findPage(filters);
        return new PageInfo(page, 5);
    }

    @Override
    public void delete(Serializable id) {
        getEntityDao().delete(id);
    }
}
