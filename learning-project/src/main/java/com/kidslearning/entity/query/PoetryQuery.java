package com.kidslearning.entity.query;


import lombok.Data;

@Data
public class PoetryQuery extends BaseQuery {
    private String keyword;
    private String dynasty;
    private String author;
    private String genre;
    private String theme;

    
}