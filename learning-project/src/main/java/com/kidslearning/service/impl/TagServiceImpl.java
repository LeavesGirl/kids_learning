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

@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Override
    public Map<String, List<String>> getTagsByType() {
        Map<String, List<String>> tagsMap = new HashMap<>();
        
        // 确保按固定顺序添加类型
        tagsMap.put("dynasty", new ArrayList<>());
        tagsMap.put("author", new ArrayList<>());
        tagsMap.put("genre", new ArrayList<>());
        tagsMap.put("theme", new ArrayList<>());
        
        // 获取所有标签
        List<Tag> tags = baseMapper.selectList(null);
        
        // 按类型分组
        for (Tag tag : tags) {
            String type = tag.getType();
            String name = tag.getName();
            
            if (tagsMap.containsKey(type)) {
                tagsMap.get(type).add(name);
            }
        }
        
        return tagsMap;
    }
}
