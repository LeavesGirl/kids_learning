package com.kidslearning.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kidslearning.entity.ChineseCharacter;
import com.kidslearning.entity.query.CharacterQuery;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CharacterMapper extends BaseMapper<ChineseCharacter> {

    Page<ChineseCharacter> selectByQuery(Page<ChineseCharacter> page, @Param("query") CharacterQuery query);
}