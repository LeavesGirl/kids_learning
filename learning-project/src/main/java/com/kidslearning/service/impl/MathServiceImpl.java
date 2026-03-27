package com.kidslearning.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kidslearning.entity.Math;
import com.kidslearning.entity.query.MathQuery;
import com.kidslearning.mapper.MathMapper;
import com.kidslearning.service.MathService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MathServiceImpl extends ServiceImpl<MathMapper, Math> implements MathService {

    @Override
    public List<Math> listData(MathQuery query) {
        return baseMapper.selectByQuery(query);
    }
}