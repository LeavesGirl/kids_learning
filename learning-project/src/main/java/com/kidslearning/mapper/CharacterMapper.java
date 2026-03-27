package com.kidslearning.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kidslearning.entity.ChineseCharacter;
import com.kidslearning.entity.query.CharacterQuery;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CharacterMapper extends BaseMapper<ChineseCharacter> {

    List<ChineseCharacter> selectByQuery(CharacterQuery query);
}