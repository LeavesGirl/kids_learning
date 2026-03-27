package com.kidslearning.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kidslearning.entity.Math;
import com.kidslearning.entity.query.MathQuery;
import com.kidslearning.service.BaseService;
import com.kidslearning.service.MathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/math")
public class MathController extends BaseController<Math, MathQuery> {

    @Autowired
    private MathService mathService;

    

}
