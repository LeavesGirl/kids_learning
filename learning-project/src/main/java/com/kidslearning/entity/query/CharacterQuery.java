package com.kidslearning.entity.query;


import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class CharacterQuery extends BaseQuery {
    
    private String keyword;

    
}