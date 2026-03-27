package com.kidslearning.entity.query;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kidslearning.entity.English;

import lombok.Data;

@Data
public class EnglishQuery extends BaseQuery {
    
    private String keyword;
 
}