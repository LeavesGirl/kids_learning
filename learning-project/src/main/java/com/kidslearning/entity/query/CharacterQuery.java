package com.kidslearning.entity.query;


import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class CharacterQuery extends BaseQuery {
    
    private String character;
    private String pinyin;
    private String category;
    private Boolean learned;

    
}
