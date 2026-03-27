package com.kidslearning.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kidslearning.entity.English;
import com.kidslearning.entity.query.EnglishQuery;
import com.kidslearning.mapper.EnglishMapper;
import com.kidslearning.service.EnglishService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnglishServiceImpl extends ServiceImpl<EnglishMapper, English> implements EnglishService {

    @Override
    public List<English> listData(EnglishQuery query) {
        return baseMapper.selectByQuery(query);
    }
}