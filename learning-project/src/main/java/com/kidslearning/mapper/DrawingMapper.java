package com.kidslearning.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kidslearning.entity.Drawing;
import com.kidslearning.entity.query.DrawingQuery;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DrawingMapper extends BaseMapper<Drawing> {

    List<Drawing> selectByQuery(DrawingQuery query);
}