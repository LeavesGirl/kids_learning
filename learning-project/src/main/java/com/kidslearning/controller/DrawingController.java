package com.kidslearning.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kidslearning.entity.Drawing;
import com.kidslearning.entity.query.DrawingQuery;
import com.kidslearning.service.BaseService;
import com.kidslearning.service.DrawingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/drawing")
public class DrawingController extends BaseController<Drawing, DrawingQuery> {

    @Autowired
    private DrawingService drawingService;
    
    @Autowired
    public void setDrawingService(DrawingService drawingService) {
        this.baseService = drawingService;
    }

}