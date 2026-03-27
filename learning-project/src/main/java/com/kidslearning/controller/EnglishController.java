package com.kidslearning.controller;

import com.kidslearning.entity.English;
import com.kidslearning.entity.query.EnglishQuery;
import com.kidslearning.service.BaseService;
import com.kidslearning.service.EnglishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/english")
public class EnglishController extends BaseController<English, EnglishQuery> {

    @Autowired
    private EnglishService englishService;
    
    @Autowired
    public void setEnglishService(EnglishService englishService) {
        this.baseService = englishService;
    }

}