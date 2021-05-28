package com.iplume.kafkastudy;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;

/**
 * kafka的消息产生类.
 *
 * @author: lingchen
 * @date: 2021/5/27
 */
public class MyProducer {

    /**
     * kafka消息生产.
     */
    private static KafkaProducer<String, String> producer;

    static {
        // kafka的属性设置.
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "hdss7-81:9092");

        // key和value的键序列化方式(StringSerializer).
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        // 自定义分区配置.
        properties.put("partitioner.class", "com.iplume.kafkastudy.CustomPartitioner");

        // 构建消息生产者.
        producer = new KafkaProducer<String, String>(properties);
    }

    /**
     * 发送kafka消息.
     */
    private static void sendMessageForgetResult() {
        ProducerRecord<String, String> record = new ProducerRecord<>(
                "iplume-kafka-study",
                "name",
                "ForgetResult"
        );

        producer.send(record);
        producer.close();
    }

    /**
     * 同步发送消息.
     */
    private static void sendMessageSync() throws Exception {

        ProducerRecord<String, String> record = new ProducerRecord<>(
                "iplume-kafka-study",
                "name",
                "Sync"
        );

        RecordMetadata result = producer.send(record).get();

        System.out.println(result.topic());
        System.out.println(result.partition());
        System.out.println(result.offset());

        producer.close();
    }

    /**
     * 异步发送消息.
     */
    private static void sendMessageCallback() {

        // ProducerRecord<String, String> record = new ProducerRecord<>(
        //         "iplume-kafka-study",
        //         "name",
        //         "Callback"
        // );

        ProducerRecord<String, String> record = new ProducerRecord<>(
                "iplume-kafka-study-x",
                "name",
                "Callback"
        );

        // 发送消息.
        producer.send(record, new MyProducerCallback());

        record = new ProducerRecord<>(
                "iplume-kafka-study-x",
                "name_x",
                "Callback_x"
        );
        producer.send(record, new MyProducerCallback());

        record = new ProducerRecord<>(
                "iplume-kafka-study-x",
                "name_y",
                "Callback_y"
        );
        producer.send(record, new MyProducerCallback());

        producer.close();
    }

    /**
     * 异步发送消息的回调处理类.
     */
    private static class MyProducerCallback implements Callback {

        @Override
        public void onCompletion(RecordMetadata recordMetadata, Exception e) {

            if (e != null) {
                e.printStackTrace();
                return;
            }

            System.out.println("Topic: " + recordMetadata.topic());
            System.out.println("Partition: " + recordMetadata.partition());
            System.out.println("Offset: " + recordMetadata.offset());

            System.out.println("Coming in MyProducerCallback");
        }
    }

    public static void main(String[] args) throws Exception {
        // sendMessageForgetResult();
        // sendMessageSync();
        sendMessageCallback();
    }
}
