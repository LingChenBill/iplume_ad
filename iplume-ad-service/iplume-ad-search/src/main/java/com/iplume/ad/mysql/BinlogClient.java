package com.iplume.ad.mysql;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.iplume.ad.mysql.listener.AggregationListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Binlog的监听客户端.
 *
 * @author: lingchen
 * @date: 2021/5/17
 */
@Slf4j
@Component
public class BinlogClient {

    /**
     * Binlog客户端.
     */
    private BinaryLogClient client;

    /**
     * Binlog相关的mysql属性设置类.
     */
    private final BinlogConfig config;

    /**
     * 收集与聚合Binlog的监听器.
     */
    private final AggregationListener listener;

    @Autowired
    public BinlogClient(BinlogConfig config, AggregationListener listener) {
        this.config = config;
        this.listener = listener;
    }

    /**
     * Binlog的监听连接.
     */
    @SuppressWarnings("AlibabaAvoidManuallyCreateThread")
    public void connect() {

        // 在一个线程中开启监听事件.
        new Thread(() -> {

            // 创建Binlog的连接客户端.
            client = new BinaryLogClient(
                    config.getHost(),
                    config.getPort(),
                    config.getUsername(),
                    config.getPassword()
            );

            // 校验是否需要设置Binlog的文件与偏移量.
            if (!StringUtils.isEmpty(config.getBinlogName())
                && !config.getPosition().equals(-1L)) {
                client.setBinlogFilename(config.getBinlogName());
                client.setBinlogPosition(config.getPosition());
            }

            // 注册监听器.
            client.registerEventListener(listener);

            try {

                log.info("connecting to mysql start");

                // 开启连接.
                client.connect();

                log.info("connecting to mysql done");

            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }).start();
    }

    /**
     * Binlog的监听断开.
     */
    public void close() {

        try {

            log.info("Disconnecting to mysql");
            client.disconnect();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
