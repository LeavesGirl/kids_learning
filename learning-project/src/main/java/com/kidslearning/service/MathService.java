package com.kidslearning.service;

import com.kidslearning.entity.Math;
import com.kidslearning.entity.query.MathQuery;

import java.util.List;

public interface MathService extends BaseService<Math, MathQuery> {
    List<Math> generateMathQuestions(int maxNumber, int count, List<String> operators);
}