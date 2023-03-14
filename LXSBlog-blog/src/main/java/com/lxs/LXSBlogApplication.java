package com.lxs;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @user 潇洒
 * @date 2023/3/8-13:49
 */
@SpringBootApplication
@MapperScan("com.lxs.mapper")
@EnableScheduling
public class LXSBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(LXSBlogApplication.class,args);
    }
}
