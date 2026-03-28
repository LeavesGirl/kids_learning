package com.kidslearning.entity.query;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kidslearning.entity.English;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class EnglishQuery extends BaseQuery {
    
    private String word;
    private String meaning;
    private String type;
    private Boolean learned;
 
}
