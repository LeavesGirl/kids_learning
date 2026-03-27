package com.kidslearning.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kidslearning.entity.Idiom;
import com.kidslearning.entity.query.IdiomQuery;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IdiomMapper extends BaseMapper<Idiom> {


    Page<Idiom> selectByQuery(Page<Idiom> page, @Param("query")IdiomQuery query);
}