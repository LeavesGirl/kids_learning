package com.kidslearning;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;

import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@MapperScan("com.kidslearning.mapper")
@SpringBootApplication
public class LearningProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(LearningProjectApplication.class, args);
	}


}
