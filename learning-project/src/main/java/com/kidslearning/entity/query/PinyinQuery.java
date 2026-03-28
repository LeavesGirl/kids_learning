package com.kidslearning.entity.query;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kidslearning.entity.Pinyin;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class PinyinQuery extends BaseQuery {
    
    private String pinyin;
    private String type;
    private Boolean learned;
    
}
