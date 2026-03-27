package com.kidslearning.entity.query;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

@Data
public class BaseQuery {
    private int pageSize = 10;
    private int pageNum = 1;


}
