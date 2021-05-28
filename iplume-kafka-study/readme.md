### kafka启动.
#### 1) 启动zookeeper.  
```text
// 后台运行(-daemon).
[root@hdss7-81 kafka]# bin/zookeeper-server-start.sh -daemon config/zookeeper.properties
[root@hdss7-81 kafka]# jps
4901 Jps
4855 QuorumPeerMain
```
#### 2) 启动kafka.  
```text
[root@hdss7-81 kafka]# bin/kafka-server-start.sh -daemon config/server.properties
[root@hdss7-81 kafka]# jps
6997 Jps
4855 QuorumPeerMain
6924 Kafka
```

#### 3) 查看消息主题.  
```text
[root@hdss7-81 kafka]# bin/kafka-topics.sh --list --zookeeper localhost:2181
__consumer_offsets
iplume-ad-test-01 
iplume-kafka-study
iplume-kafka-study-x

```
#### 4) 创建消息Topic
```text
[root@hdss7-81 kafka]# bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 3 --topic iplume-kafka-study-x
```

#### 5) 创建生产者.
```text
[root@hdss7-81 kafka]# bin/kafka-console-producer.sh --broker-list localhost:9092 --topic iplume-kafka-study-x
```

#### 6) 创建消费者.
```text
[root@hdss7-81 kafka]# bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic iplume-kafka-study-x --form-beginning
```
#### 7) 关闭kafka和zookeeper.
```text
[root@hdss7-81 kafka]# bin/kafka-server-stop.sh 
[root@hdss7-81 kafka]# bin/zookeeper-server-stop.sh 
[root@hdss7-81 kafka]# jps
33018 Jps
```
