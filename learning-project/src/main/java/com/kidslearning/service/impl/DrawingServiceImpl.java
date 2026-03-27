package com.kidslearning.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kidslearning.entity.Drawing;
import com.kidslearning.entity.query.DrawingQuery;
import com.kidslearning.mapper.DrawingMapper;
import com.kidslearning.service.DrawingService;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class DrawingServiceImpl extends ServiceImpl<DrawingMapper, Drawing> implements DrawingService {

    @Override
    public List<Drawing> listData(DrawingQuery query) {
        return baseMapper.selectByQuery( query);
    }
}