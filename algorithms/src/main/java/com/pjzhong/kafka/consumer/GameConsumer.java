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

  public GameConsumer(Properties properties,
      Consumer<ConsumerRecords<String, byte[]>> messageHandle) {
    this.messageHandle = messageHandle;
    this.kafkaConsumer = new KafkaConsumer<>(properties);
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
      if (kafkaConsumer != null) {
        kafkaConsumer.close();
      }
    }
  }

  public void close() {
    stop = true;
  }
}
