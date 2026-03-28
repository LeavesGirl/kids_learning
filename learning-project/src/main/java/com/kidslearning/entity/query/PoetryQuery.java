package com.kidslearning.entity.query;


import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class PoetryQuery extends BaseQuery {
    private String title;
    private String content;
    private String author;
    private String dynasty;
    private String genre;
    private String theme;
    private Boolean learned;

    
}
