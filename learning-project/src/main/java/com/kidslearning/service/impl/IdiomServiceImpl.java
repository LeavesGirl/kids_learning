package com.kidslearning.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kidslearning.entity.Idiom;
import com.kidslearning.entity.Idiom;
import com.kidslearning.entity.query.IdiomQuery;
import com.kidslearning.entity.query.IdiomQuery;
import com.kidslearning.mapper.IdiomMapper;
import com.kidslearning.service.IdiomService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IdiomServiceImpl extends ServiceImpl<IdiomMapper, Idiom> implements IdiomService {

    @Override
    public Page<Idiom> listPage(Page<Idiom> page, IdiomQuery query) {
        return baseMapper.selectByQuery(page, query);
    }
}