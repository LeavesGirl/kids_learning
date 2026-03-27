package com.kidslearning.entity.query;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kidslearning.entity.Idiom;

import lombok.Data;

@Data
public class IdiomQuery extends BaseQuery {
    
    private String keyword;
 
}