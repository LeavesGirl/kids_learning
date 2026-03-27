package com.kidslearning.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kidslearning.entity.Idiom;
import com.kidslearning.entity.query.IdiomQuery;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IdiomMapper extends BaseMapper<Idiom> {

    List<Idiom> selectByQuery(IdiomQuery query);
}