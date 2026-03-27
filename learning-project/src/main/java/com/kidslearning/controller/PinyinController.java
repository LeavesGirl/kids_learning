package com.kidslearning.controller;

import com.kidslearning.entity.Pinyin;
import com.kidslearning.entity.query.PinyinQuery;
import com.kidslearning.service.BaseService;
import com.kidslearning.service.PinyinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pinyin")
public class PinyinController extends BaseController<Pinyin, PinyinQuery> {

    @Autowired
    private PinyinService pinyinService;

  

}