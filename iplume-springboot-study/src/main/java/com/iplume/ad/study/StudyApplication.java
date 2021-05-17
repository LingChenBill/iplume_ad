package com.iplume.ad.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * spring boot study starter application.
 *
 * @description:
 * @author: lingchen
 * @date: 2021/4/17
 */
@EnableScheduling
@SpringBootApplication
public class StudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudyApplication.class, args);
    }
}
