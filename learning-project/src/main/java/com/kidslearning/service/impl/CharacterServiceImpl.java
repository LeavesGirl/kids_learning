package com.kidslearning.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kidslearning.entity.ChineseCharacter;
import com.kidslearning.entity.query.CharacterQuery;
import com.kidslearning.mapper.CharacterMapper;
import com.kidslearning.service.CharacterService;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class CharacterServiceImpl extends ServiceImpl<CharacterMapper, ChineseCharacter> implements CharacterService {

    @Override
    public List<ChineseCharacter> listData(CharacterQuery query) {
        return baseMapper.selectByQuery( query);
    }
}