package com.kidslearning.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kidslearning.entity.Math;
import com.kidslearning.entity.query.MathQuery;
import com.kidslearning.mapper.MathMapper;
import com.kidslearning.service.MathService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class MathServiceImpl extends ServiceImpl<MathMapper, Math> implements MathService {

    @Override
    public Page<Math> listPage(Page<Math> page, MathQuery query) {
        return baseMapper.selectByQuery(page, query);
    }

    @Override
    public List<Math> generateMathQuestions(int maxNumber, int count, List<String> operators) {
        List<Math> questions = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < count; i++) {
            int operatorIndex = random.nextInt(operators.size());
            String operator = operators.get(operatorIndex);
            
            int num1, num2;
            String content, solution;
            
            switch (operator) {
                case "add":
                    num1 = random.nextInt(maxNumber + 1);
                    num2 = random.nextInt(maxNumber + 1);
                    content = num1 + " + " + num2 + " = ?";
                    solution = num1 + " + " + num2 + " = " + (num1 + num2);
                    break;
                case "subtract":
                    num1 = random.nextInt(maxNumber + 1);
                    num2 = random.nextInt(num1 + 1);
                    content = num1 + " - " + num2 + " = ?";
                    solution = num1 + " - " + num2 + " = " + (num1 - num2);
                    break;
                case "multiply":
                    num1 = random.nextInt(maxNumber + 1);
                    num2 = random.nextInt(maxNumber + 1);
                    content = num1 + " × " + num2 + " = ?";
                    solution = num1 + " × " + num2 + " = " + (num1 * num2);
                    break;
                case "divide":
                    num2 = random.nextInt(maxNumber) + 1;
                    num1 = num2 * random.nextInt(maxNumber + 1);
                    content = num1 + " ÷ " + num2 + " = ?";
                    solution = num1 + " ÷ " + num2 + " = " + (num1 / num2);
                    break;
                default:
                    continue;
            }
            
            Math math = new Math();
            math.setTitle("算术题 " + (i + 1));
            math.setContent(content);
            math.setSolution(solution);
            
            save(math);
            questions.add(math);
        }
        
        return questions;
    }
}