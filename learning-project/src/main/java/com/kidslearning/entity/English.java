package com.kidslearning.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("english")
public class English {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String word;
    private String pronunciation;
    private String meaning;
    private String example;
    private String type;
    private Boolean learned;

}
