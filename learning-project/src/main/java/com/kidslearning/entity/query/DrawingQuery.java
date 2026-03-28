package com.kidslearning.entity.query;


import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class DrawingQuery extends BaseQuery {
    
    private String title;
    private String startDate;
    private String endDate;
 
}