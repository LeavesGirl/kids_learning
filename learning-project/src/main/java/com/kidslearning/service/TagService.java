package com.kidslearning.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kidslearning.entity.Tag;
import java.util.List;
import java.util.Map;

public interface TagService extends IService<Tag> {
    Map<String, List<Tag>> getTagsByType();
}
