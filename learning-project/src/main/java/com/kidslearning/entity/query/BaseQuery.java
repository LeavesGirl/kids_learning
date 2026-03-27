package com.kidslearning.entity.query;

import lombok.Data;

@Data
public class BaseQuery {
    private int pageSize = 10;
    private int pageNum = 1;


}
