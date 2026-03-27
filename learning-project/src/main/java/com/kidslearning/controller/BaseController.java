package com.kidslearning.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.kidslearning.entity.query.BaseQuery;
import com.kidslearning.service.BaseService;

import lombok.Setter;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 基础控制器，提取通用的列表页、列表数据查询和详情页方法
 * @param <T> 实体类型
 * @param <Q> 查询对象类型
 */
@Controller
public abstract class BaseController<T, Q extends BaseQuery> {
    private String moduleName;

    /**
     * 基础Service实例
     */
    @Setter
    private BaseService<T, Q> baseService;

    /**
     * 构造器，获取泛型T的实际类型
     */
    @SuppressWarnings("unchecked")
    public BaseController() {
        // 获取泛型T的实际类型
        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            ParameterizedType paramType = (ParameterizedType) type;
            Class<T> entityClass = (Class<T>) paramType.getActualTypeArguments()[0];
            // 获取模块名称（将首字母小写）
            String className = entityClass.getSimpleName();
            this.moduleName = className.substring(0, 1).toLowerCase() + className.substring(1);
        } else {
            // 如果无法获取泛型类型，使用默认值
            this.moduleName = "unknown";
        }
    }

    /**
     * 列表页
     * @param model 模型
     * @return 视图名称
     */
    @GetMapping("/list")
    public String list(Model model) {
        return moduleName + "/list";
    }

    /**
     * 列表数据查询
     * @param query 查询对象
     * @return 分页数据
     */
    @GetMapping("/list/data")
    @ResponseBody
    public Page<T> listData(Q query) {
        Page<T> page = new Page<>(query.getPageNum(), query.getPageSize());
        page.setRecords(baseService.listData(query));
        return page;
    }

    /**
     * 详情页
     * @param id 主键
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