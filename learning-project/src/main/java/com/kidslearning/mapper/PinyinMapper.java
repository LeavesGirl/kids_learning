package com.kidslearning.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kidslearning.entity.Pinyin;
import com.kidslearning.entity.query.PinyinQuery;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PinyinMapper extends BaseMapper<Pinyin> {


    Page<Pinyin> selectByQuery(Page<Pinyin> page, @Param("query")PinyinQuery query);
}