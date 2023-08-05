package com.lxs;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @user 潇洒
 * @date 2023/3/8-13:49
 */
@SpringBootApplication
@MapperScan("com.lxs.mapper")
@EnableScheduling
@EnableSwagger2
//@EnableDiscoveryClient
@EnableDiscoveryClient
public class LXSBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(LXSBlogApplication.class,args);
    }
}
