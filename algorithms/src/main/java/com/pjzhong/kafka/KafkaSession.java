package com.pjzhong.kafka;

import com.pjzhong.kafka.consumer.GameConsumer;
import com.pjzhong.kafka.producer.GameProducer;
import java.util.Collections;
import java.util.function.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.ProducerRecord;

public class KafkaSession implements Runnable {

  private String id;
  private GameProducer producer;
  private GameConsumer consumer;

  public KafkaSession(String id, Consumer<ConsumerRecords<String, byte[]>> messageHandle) {
    this.id = id;
    this.consumer = new GameConsumer(id, messageHandle);
    this.producer = new GameProducer();
  }

  @Override
  public void run() {
    this.consumer.subscribe(Collections.singleton(id));
    this.consumer.run();
  }


  public void send(String topics, byte[] dates) {
    producer.send(new ProducerRecord<>(topics, dates));
  }

  public void send(ProducerRecord<String, byte[]> record) {
    producer.send(record);
  }

  public void close() {
    consumer.close();
    producer.close();
  }
}
