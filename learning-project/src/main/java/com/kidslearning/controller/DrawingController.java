package com.kidslearning.controller;

import com.kidslearning.entity.Drawing;
import com.kidslearning.entity.query.DrawingQuery;
import com.kidslearning.service.DrawingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/drawing")
public class DrawingController extends BaseController<Drawing, DrawingQuery> {

    @Autowired
    private DrawingService drawingService;
    
    @Autowired
    public void setDrawingService(DrawingService drawingService) {
        this.baseService = drawingService;
    }

}