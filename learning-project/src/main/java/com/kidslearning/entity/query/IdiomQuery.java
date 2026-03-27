package com.kidslearning.entity.query;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kidslearning.entity.Idiom;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class IdiomQuery extends BaseQuery {
    
    private String keyword;
 
}