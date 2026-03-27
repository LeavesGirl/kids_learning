package com.kidslearning.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kidslearning.entity.Poetry;
import com.kidslearning.entity.Poetry;
import com.kidslearning.entity.query.PoetryQuery;
import com.kidslearning.entity.query.PoetryQuery;
import com.kidslearning.mapper.PoetryMapper;
import com.kidslearning.service.PoetryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PoetryServiceImpl extends ServiceImpl<PoetryMapper, Poetry> implements PoetryService {
    @Override
    public Page<Poetry> listPage(Page<Poetry> page, PoetryQuery query) {
        return baseMapper.selectByQuery(page, query);
    }
}