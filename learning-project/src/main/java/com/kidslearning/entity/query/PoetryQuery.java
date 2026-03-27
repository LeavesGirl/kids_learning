package com.kidslearning.entity.query;


import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class PoetryQuery extends BaseQuery {
    private String keyword;
    private String dynasty;
    private String author;
    private String genre;
    private String theme;

    
}