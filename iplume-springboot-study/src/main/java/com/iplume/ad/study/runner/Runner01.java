package com.iplume.ad.study.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * CommandLineRunner runner.
 *
 * @description: 可以通过 @Order(value = 1)来定制执行顺序.
 * @author: lingchen
 * @date: 2021/4/17
 */
@Order(value = 1)
@Slf4j
@Component
public class Runner01 implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        log.info("Running runner01...");
    }
}
