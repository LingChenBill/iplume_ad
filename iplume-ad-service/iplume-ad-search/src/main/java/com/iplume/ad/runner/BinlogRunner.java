package com.iplume.ad.runner;

import com.iplume.ad.mysql.BinlogClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Binlog监听的启动类.
 *
 * @author: lingchen
 * @date: 2021/5/17
 */
@Slf4j
@Component
public class BinlogRunner implements CommandLineRunner {

    /**
     * Binlog的监听客户端.
     */
    private final BinlogClient client;

    @Autowired
    public BinlogRunner(BinlogClient client) {
        this.client = client;
    }

    /**
     * Binlog监听启动.
     *
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {

        log.info("Coming in BinlogRunner...");
        client.connect();
    }
}
