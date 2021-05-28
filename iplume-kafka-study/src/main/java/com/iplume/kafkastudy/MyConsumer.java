package com.iplume.kafkastudy;

import org.apache.kafka.clients.consumer.CommitFailedException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Collections;
import java.util.Properties;

/**
 * kafka的消费类.
 *
 * @author: lingchen
 * @date: 2021/5/28
 */
public class MyConsumer {

    /**
     * kafka消费者.
     */
    private static KafkaConsumer<String, String> consumer;

    /**
     * 属性.
     */
    private static Properties properties;

    static {
        properties = new Properties();
        properties.put("bootstrap.servers", "hdss7-81:9092");

        // key 与 value的反序列化.
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        // kafka不同组也可以消费全量的消息.
        properties.put("group.id", "KafkaStudy");
    }

    /**
     * 消息消费方式: 自动提交位移.
     */
    private static void generalConsumerMessageAutoCommit() {

        properties.put("enable.auto.commit", true);
        consumer = new KafkaConsumer<>(properties);

        consumer.subscribe(Collections.singleton("iplume-kafka-study-x"));

        try {
            while (true) {
                boolean flg = true;

                // 拉取消息.
                ConsumerRecords<String, String> records = consumer.poll(100);

                for (ConsumerRecord<String, String> record : records) {
                    System.out.println(String.format("" +
                                    "Topic: %s, Partition: %s, Key: %s, Value: %s",
                            record.topic(),
                            record.partition(),
                            record.key(),
                            record.value()));

                    if (record.value().equals("done")) {
                        flg = false;
                    }
                }

                if (!flg) {
                    break;
                }
            }
        } finally {
            consumer.close();
        }
    }

    /**
     * 手动提交当前位移(同步提交).
     */
    @SuppressWarnings("DuplicatedCode")
    private static void generalConsumeMessageSyncCommit() {

        // 设置自动提交为false.
        properties.put("auto.commit.offset", false);
        // 消费者实例化.
        consumer = new KafkaConsumer<>(properties);
        // 订阅消息主题.
        consumer.subscribe(Collections.singleton("iplume-kafka-study-x"));

        try {
            while (true) {
                boolean flg = true;
                ConsumerRecords<String, String> recordss = consumer.poll(100);

                for (ConsumerRecord<String, String> record : recordss) {
                    System.out.println(String.format("" +
                                    "Topic: %s, Partition: %s, Key: %s, Value: %s",
                            record.topic(),
                            record.partition(),
                            record.key(),
                            record.value()));

                    if (record.value().equals("done")) {
                        flg = false;
                    }
                }

                try {
                    // 同步提交发生消息堵塞.直到消费消息或者是发生异常为止.
                    consumer.commitSync();
                } catch (CommitFailedException ex) {
                    System.out.println("Commit fail error: " + ex.getMessage());
                }

                if (!flg) {
                    break;
                }
            }
        } finally {
            consumer.close();
        }
    }

    /**
     * 异步消费消息.
     */
    public static void generalConsumerMessageAsyncCommit() {
        // 设置自动提交为false.
        properties.put("auto.commit.offset", false);
        // 消费者实例化.
        consumer = new KafkaConsumer<>(properties);
        // 订阅消息主题.
        consumer.subscribe(Collections.singleton("iplume-kafka-study-x"));

        try {
            while (true) {
                boolean flg = true;
                ConsumerRecords<String, String> recordss = consumer.poll(100);

                for (ConsumerRecord<String, String> record : recordss) {
                    System.out.println(String.format("" +
                                    "Topic: %s, Partition: %s, Key: %s, Value: %s",
                            record.topic(),
                            record.partition(),
                            record.key(),
                            record.value()));

                    if (record.value().equals("done")) {
                        flg = false;
                    }
                }

                // 异步提交消息不会发行堵塞,但不能保证能正确消费消息.
                consumer.commitAsync();

                if (!flg) {
                    break;
                }
            }
        } finally {
            consumer.close();
        }
    }

    /**
     * 手动异步提交消息带回调.
     */
    @SuppressWarnings("AlibabaRemoveCommentedCode")
    public static void generalConsumeMessageAsyncCommitWithCallback() {

        // 设置自动提交为false.
        properties.put("auto.commit.offset", false);
        // 消费者实例化.
        consumer = new KafkaConsumer<>(properties);
        // 订阅消息主题.
        consumer.subscribe(Collections.singleton("iplume-kafka-study-x"));

        try {
            while (true) {
                boolean flg = true;
                ConsumerRecords<String, String> recordss = consumer.poll(100);

                for (ConsumerRecord<String, String> record : recordss) {
                    System.out.println(String.format("" +
                                    "Topic: %s, Partition: %s, Key: %s, Value: %s",
                            record.topic(),
                            record.partition(),
                            record.key(),
                            record.value()));

                    if (record.value().equals("done")) {
                        flg = false;
                    }
                }

                // 正常写法.
                // consumer.commitAsync(new OffsetCommitCallback() {
                //     @Override
                //     public void onComplete(Map<TopicPartition, OffsetAndMetadata> offsets, Exception exception) {
                //         // 异步提交失败后的回调,查看错误信息.
                //         if (exception != null) {
                //             System.out.println("Commit failed for offsets: " + exception.getMessage());
                //         }
                //     }
                // });

                // lambda语法糖.
                consumer.commitAsync((offsets, exception) -> {
                    // 异步提交失败后的回调,查看错误信息.
                    if (exception != null) {
                        System.out.println("Commit failed for offsets: " + exception.getMessage());
                    }
                });

                if (!flg) {
                    break;
                }
            }
        } finally {
            consumer.close();
        }
    }

    /**
     * 混合异步与同步提交位移.
     */
    @SuppressWarnings("DuplicatedCode")
    public static void mixAsyncAndSyncCommit() {
        // 设置自动提交为false.
        properties.put("auto.commit.offset", false);
        // 消费者实例化.
        consumer = new KafkaConsumer<>(properties);
        // 订阅消息主题.
        consumer.subscribe(Collections.singleton("iplume-kafka-study-x"));

        try {
            while (true) {
                boolean flg = true;
                ConsumerRecords<String, String> recordss = consumer.poll(100);

                for (ConsumerRecord<String, String> record : recordss) {
                    System.out.println(String.format("" +
                                    "Topic: %s, Partition: %s, Key: %s, Value: %s",
                            record.topic(),
                            record.partition(),
                            record.key(),
                            record.value()));

                    if (record.value().equals("done")) {
                        flg = false;
                    }
                }

                // 异步提交.
                consumer.commitAsync();

                if (!flg) {
                    break;
                }
            }
        } catch (Exception ex) {
            System.out.println("Commit async error: " + ex.getMessage());
        } finally {
            try {
                // 同步提交.
                consumer.commitSync();
            } finally {
                consumer.close();
            }
        }
    }

    public static void main(String[] args) {
        // 自动提交位移.
        // generalConsumerMessageAutoCommit();
        // generalConsumeMessageSyncCommit();
        // generalConsumerMessageAsyncCommit();

        // generalConsumeMessageAsyncCommitWithCallback();
        mixAsyncAndSyncCommit();
    }
}
