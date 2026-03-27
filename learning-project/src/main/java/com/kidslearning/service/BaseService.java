package com.kidslearning.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 基础Service接口，定义通用的列表数据查询方法
 *
 * @param <T> 实体类型
 * @param <Q> 查询对象类型
 */
public interface BaseService<T, Q> extends IService<T> {
    /**
     * 列表数据查询
     *
     * @param query 查询对象
     * @return 分页数据
     */

    Page<T> listPage(Page<T> page, Q query);

}