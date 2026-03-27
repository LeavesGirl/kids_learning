package com.kidslearning.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kidslearning.entity.English;
import com.kidslearning.entity.query.EnglishQuery;
import com.kidslearning.mapper.EnglishMapper;
import com.kidslearning.service.EnglishService;
import org.springframework.stereotype.Service;


@Service
public class EnglishServiceImpl extends ServiceImpl<EnglishMapper, English> implements EnglishService {
    @Override
    public Page<English> listPage(Page<English> page, EnglishQuery query) {
        return baseMapper.selectByQuery(page, query);
    }
}