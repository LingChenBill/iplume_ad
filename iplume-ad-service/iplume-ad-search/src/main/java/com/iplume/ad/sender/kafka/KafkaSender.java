package com.iplume.ad.sender.kafka;

import com.alibaba.fastjson.JSON;
import com.iplume.ad.mysql.dto.MysqlRowData;
import com.iplume.ad.sender.ISender;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * 往Kafka中投递信息.
 *
 * @author: lingchen
 * @date: 2021/5/20
 */
@Slf4j
@Component("kafkaSender")
public class KafkaSender implements ISender {

    @Value("adconf.kafka.topic")
    private String topic;

    /**
     * KafkaTemplate.
     */
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public KafkaSender(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Kafka消息投递.
     *
     * @param rowData 消息数据.
     */
    @Override
    public void sender(MysqlRowData rowData) {
        kafkaTemplate.send(
                topic,
                JSON.toJSONString(rowData)
        );
    }

    /**
     * Kafka消息监听.
     *
     * @param record
     */
    @KafkaListener(topics = {"ad-search-mysql-data"}, groupId = "ad-search")
    public void processMysqlRowData(ConsumerRecord<?, ?> record) {
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            Object message = kafkaMessage.get();
            MysqlRowData rowData = JSON.parseObject(
                    message.toString(),
                    MysqlRowData.class);
            // 将接收的消息打印出来.
            log.info("kafka processMysqlRowData: {}", JSON.toJSONString(rowData));
        }
    }

}
