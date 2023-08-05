package com.lxs;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @user 潇洒
 * @date 2023/3/15-13:22
 */

@SpringBootApplication
@MapperScan("com.lxs.mapper")
@EnableDiscoveryClient
public class LXSAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(LXSAdminApplication.class,args);
    }
}
