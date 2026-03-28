package com.kidslearning.controller;

import com.kidslearning.entity.Idiom;
import com.kidslearning.entity.query.IdiomQuery;
import com.kidslearning.service.BaseService;
import com.kidslearning.service.IdiomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/idiom")
public class IdiomController extends BaseController<Idiom, IdiomQuery> {

    @Autowired
    private IdiomService idiomService;
    
    @Autowired
    public void setIdiomService(IdiomService idiomService) {
        this.baseService = idiomService;
    }


}