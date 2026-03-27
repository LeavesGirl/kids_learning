package com.kidslearning.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kidslearning.entity.Idiom;
import com.kidslearning.entity.query.IdiomQuery;
import com.kidslearning.mapper.IdiomMapper;
import com.kidslearning.service.IdiomService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IdiomServiceImpl extends ServiceImpl<IdiomMapper, Idiom> implements IdiomService {

    @Override
    public List<Idiom> listData(IdiomQuery query) {
        return baseMapper.selectByQuery(query);
    }
}