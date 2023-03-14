package com.lxs.job;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @user 潇洒
 * @date 2023/3/14-13:10
 */
@Component
public class TestJob {

    @Scheduled(cron = "0/5 * * * * ?")
    public void testJob(){
        //System.out.println("定时任务执行了");
    }

}
