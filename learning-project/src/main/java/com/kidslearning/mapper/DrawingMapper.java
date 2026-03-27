package com.kidslearning.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kidslearning.entity.Drawing;
import com.kidslearning.entity.query.DrawingQuery;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DrawingMapper extends BaseMapper<Drawing> {

    Page<Drawing> selectByQuery(Page<Drawing> page, @Param("query")DrawingQuery query);
}