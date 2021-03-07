package com.pjzhong.kafka.producer;

import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class GameProducer {

  private Producer<String, byte[]> producer;

  public GameProducer(Properties config) {
    producer = new KafkaProducer<>(config);
  }

  public void send(String topics, byte[] dates) {
    producer.send(new ProducerRecord<>(topics, dates));
  }

  public void send(ProducerRecord<String, byte[]> record) {
    producer.send(record);
  }

  public void close() {
    if (producer != null) {
      producer.close();
    }
  }

}
