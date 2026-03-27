package com.kidslearning.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kidslearning.entity.Math;
import com.kidslearning.entity.query.MathQuery;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MathMapper extends BaseMapper<Math> {


    Page<Math> selectByQuery(Page<Math> page, @Param("query") MathQuery query);
}