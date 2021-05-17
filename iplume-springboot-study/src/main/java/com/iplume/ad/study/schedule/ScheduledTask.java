package com.iplume.ad.study.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务配置.
 *
 * @description:
 * @author: lingchen
 * @date: 2021/4/17
 */
@Slf4j
@Component
public class ScheduledTask {

    // @Scheduled(cron = "*/5 * * * * *")   通过 crontab 表达式定义规则
    /**
     * @Scheduled(fixedRate = 5000)         上一次开始执行时间点之后5秒再执行
     * @Scheduled(fixedDelay = 5000)        上一次执行完毕时间点之后5秒再执行
     */
    @Scheduled(fixedRate = 1000)
    public void setScheduleTask() {
        log.info("Hello Schedule Task...");
    }
}
