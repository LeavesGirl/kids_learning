package com.kidslearning.entity.query;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kidslearning.entity.Pinyin;

import lombok.Data;

@Data
public class PinyinQuery extends BaseQuery {
    
    private String keyword;
 
}