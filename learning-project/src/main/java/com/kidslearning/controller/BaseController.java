package com.kidslearning.controller;

import cn.hutool.core.util.ClassUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kidslearning.entity.query.BaseQuery;
import com.kidslearning.service.BaseService;

import lombok.Setter;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.Serializable;

/**
 * 基础控制器，提取通用的列表页、列表数据查询和详情页方法
 *
 * @param <T> 实体类型
 * @param <Q> 查询对象类型
 */
public abstract class BaseController<T, Q extends BaseQuery> {
    private String moduleName;

    /**
     * 基础Service实例
     */
    @Setter
    protected BaseService<T, Q> baseService;

    /**
     * 构造器，获取泛型T的实际类型
     */
    public BaseController() {
        Class<?> typeArgument = ClassUtil.getTypeArgument(getClass(), 0);
        this.moduleName = typeArgument.getSimpleName();

    }

    /**
     * 列表页
     *
     * @param model 模型
     * @return 视图名称
     */
    @GetMapping("/list")
    public String list(Model model) {
        return moduleName + "/list";
    }

    /**
     * 列表数据查询
     *
     * @param query 查询对象
     * @return 分页数据
     */
    @GetMapping("/list/data")
    @ResponseBody
    public Page<T> listData(Q query) {
        return baseService.listPage(Page.of(query.getPageNum(), query.getPageSize()), query);
    }

    /**
     * 详情页
     *
     * @param id    主键
     * @param model 模型
     * @return 视图名称
     */
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Serializable id, Model model) {
        T entity = baseService.getById(id);
        model.addAttribute("entity", entity);
        return moduleName + "/detail";
    }


}