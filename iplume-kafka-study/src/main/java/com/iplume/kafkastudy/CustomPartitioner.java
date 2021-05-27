package com.iplume.kafkastudy;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.record.InvalidRecordException;
import org.apache.kafka.common.utils.Utils;

import java.util.List;
import java.util.Map;

/**
 * 自定义分区分配类.
 *
 * @author: lingchen
 * @date: 2021/5/27
 */
public class CustomPartitioner implements Partitioner {

    private static final String KEY_NAME = "name";

    /**
     * 自定义分区.
     *
     * @param topic
     * @param key
     * @param keyBytes
     * @param value
     * @param valueBytes
     * @param cluster
     * @return
     */
    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {

        List<PartitionInfo> partitionInfos = cluster.partitionsForTopic(topic);
        int partitions = partitionInfos.size();

        if (keyBytes == null || !(key instanceof String)) {
            throw new InvalidRecordException("Kafka message must have key.");
        }

        if (partitions == 1) {
            return 0;
        }

        if (KEY_NAME.equals(key)) {
            return partitions - 1;
        }

        return Math.abs(Utils.murmur2(keyBytes) % (partitions - 1));
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> configs) {

    }
}
