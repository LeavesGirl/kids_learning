package com.kidslearning.entity.query;


import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class DrawingQuery extends BaseQuery {
    
    private String keyword;
 
}