package com.kidslearning.entity.query;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

@Data
public class MathQuery extends BaseQuery {
     
    private String keyword;

    
}