package com.kidslearning.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kidslearning.entity.Tag;
import com.kidslearning.mapper.TagMapper;
import com.kidslearning.service.TagService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Override
    public Map<String, List<Tag>> getTagsByType() {
        // 获取所有标签
        List<Tag> tags = baseMapper.selectList(null);
        return tags.stream().collect(Collectors.groupingBy(Tag::getType));

    }
}
