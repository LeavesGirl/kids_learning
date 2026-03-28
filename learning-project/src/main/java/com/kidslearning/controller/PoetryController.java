package com.kidslearning.controller;

import com.kidslearning.entity.Poetry;
import com.kidslearning.entity.query.PoetryQuery;
import com.kidslearning.service.BaseService;
import com.kidslearning.service.PoetryService;
import com.kidslearning.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/poetry")
public class PoetryController extends BaseController<Poetry, PoetryQuery> {

    @Autowired
    private PoetryService poetryService;
    
    @Autowired
    private TagService tagService;
    
    @Autowired
    public void setPoetryService(PoetryService poetryService) {
        this.baseService = poetryService;
    }

    @GetMapping("/tags")
    public Object tags() {
        // 从数据库查询标签数据
        return tagService.getTagsByType();
    }

   

}