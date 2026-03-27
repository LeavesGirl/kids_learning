package com.kidslearning.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kidslearning.entity.Pinyin;
import com.kidslearning.entity.Pinyin;
import com.kidslearning.entity.query.PinyinQuery;
import com.kidslearning.entity.query.PinyinQuery;
import com.kidslearning.mapper.PinyinMapper;
import com.kidslearning.service.PinyinService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PinyinServiceImpl extends ServiceImpl<PinyinMapper, Pinyin> implements PinyinService {

    @Override
    public Page<Pinyin> listPage(Page<Pinyin> page, PinyinQuery query) {
        return baseMapper.selectByQuery(page, query);
    }
}