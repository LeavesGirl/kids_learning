package com.kidslearning.controller;

import com.kidslearning.entity.Pinyin;
import com.kidslearning.entity.query.PinyinQuery;
import com.kidslearning.service.BaseService;
import com.kidslearning.service.PinyinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pinyin")
public class PinyinController extends BaseController<Pinyin, PinyinQuery> {

    @Autowired
    private PinyinService pinyinService;
    
    @Autowired
    public void setPinyinService(PinyinService pinyinService) {
        this.baseService = pinyinService;
    }

  

}