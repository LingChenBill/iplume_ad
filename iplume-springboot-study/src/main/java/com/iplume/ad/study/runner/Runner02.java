package com.iplume.ad.study.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * ApplicationRunner runner.
 *
 * @description: 执行顺序: ApplicationRunner > CommandLineRunner
 * @author: lingchen
 * @date: 2021/4/17
 */
@Order(value = 2)
@Slf4j
@Component
public class Runner02 implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
          log.info("Running runner02...");
    }
}
