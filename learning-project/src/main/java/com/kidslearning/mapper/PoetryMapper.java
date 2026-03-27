package com.kidslearning.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kidslearning.entity.Poetry;
import com.kidslearning.entity.query.PoetryQuery;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PoetryMapper extends BaseMapper<Poetry> {


    Page<Poetry> selectByQuery(Page<Poetry> page,@Param("query") PoetryQuery query);
}