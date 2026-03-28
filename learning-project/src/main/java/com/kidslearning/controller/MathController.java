package com.kidslearning.controller;

import com.kidslearning.entity.Math;
import com.kidslearning.entity.query.MathQuery;
import com.kidslearning.service.MathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/math")
public class MathController extends BaseController<Math, MathQuery> {

    @Autowired
    private MathService mathService;
    
    @Autowired
    public void setMathService(MathService mathService) {
        this.baseService = mathService;
    }

    @PostMapping("/generate")
    public List<Math> generateQuestions(@RequestBody Map<String, Object> params) {
        int maxNumber = Integer.parseInt(params.get("maxNumber").toString());
        int count = Integer.parseInt(params.get("count").toString());
        @SuppressWarnings("unchecked")
        List<String> operators = (List<String>) params.get("operators");
        return mathService.generateMathQuestions(maxNumber, count, operators);
    }

}
