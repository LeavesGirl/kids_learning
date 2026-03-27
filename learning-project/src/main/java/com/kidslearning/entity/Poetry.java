package com.kidslearning.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("poetry")
public class Poetry {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String title;
    private String author;
    private String content;
    private String explanation;
    private String dynasty;

}