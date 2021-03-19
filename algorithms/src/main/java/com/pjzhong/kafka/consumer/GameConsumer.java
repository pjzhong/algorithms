package com.pjzhong.kafka.consumer;

import java.time.Duration;
import java.util.Collection;
import java.util.Properties;
import java.util.function.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class GameConsumer implements Runnable {

  /** 消息处理 */
  private Consumer<ConsumerRecords<String, byte[]>> messageHandle;
  /** 消息队列处理器 */
  private KafkaConsumer<String, byte[]> kafkaConsumer;
  /** 已经停止 */
  private boolean stop;

  public GameConsumer(String id,
      Consumer<ConsumerRecords<String, byte[]>> messageHandle) {
    Properties consumerConf = new Properties();
    consumerConf.setProperty("bootstrap.servers", "localhost:9092");
    //全部consumer保持同一个组，一个topics下只有一个partition，保证不被多个consumer消耗
    //然后每个consumer只监听自己的topic
    consumerConf.setProperty("group.id", "test");
    consumerConf.setProperty("client.id", id);
    consumerConf.setProperty("enable.auto.commit", "true");
    consumerConf.setProperty("auto.commit.interval.ms", "1000");
    consumerConf.setProperty("key.deserializer",
        "org.apache.kafka.common.serialization.StringDeserializer");
    consumerConf.setProperty("value.deserializer",
        "org.apache.kafka.common.serialization.ByteArrayDeserializer");
    consumerConf.setProperty("metadata.max.age.ms", "100000");//100秒更新配置信息，用以消费新topic。
    this.messageHandle = messageHandle;
    this.kafkaConsumer = new KafkaConsumer<>(consumerConf);
  }

  public GameConsumer subscribe(Collection<String> topics) {
    if (kafkaConsumer == null) {
      throw new RuntimeException("consumer is null, can't not subscribe");
    }
    kafkaConsumer.subscribe(topics);

    return this;
  }

  @Override
  public void run() {
    try {
      while (!stop) {
        ConsumerRecords<String, byte[]> records = kafkaConsumer.poll(Duration.ofMillis(100));
        if (!records.isEmpty()) {
          messageHandle.accept(records);
        }
      }
    } finally {
      if (kafkaConsumer != null && stop) {
        kafkaConsumer.close();
      }
    }
  }

  public void close() {
    stop = true;
  }
}
