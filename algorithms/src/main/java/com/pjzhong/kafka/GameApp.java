package com.pjzhong.kafka;

import com.pjzhong.kafka.consumer.GameConsumer;
import com.pjzhong.kafka.producer.GameProducer;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Properties;
import java.util.Scanner;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;

public class GameApp {

  public static void main(String[] args) {
    if (args.length != 1) {
      throw new RuntimeException("required A topic");
    }
    Properties consumerConf = new Properties();
    consumerConf.setProperty("bootstrap.servers", "localhost:9092");
    //全部consumer保持同一个组，一个topics下只有一个partition，保证不被多个consumer消耗
    //然后每个consumer只监听自己的topic
    consumerConf.setProperty("group.id", "test");
    consumerConf.setProperty("client.id", args[0]);
    consumerConf.setProperty("enable.auto.commit", "true");
    consumerConf.setProperty("auto.commit.interval.ms", "1000");
    consumerConf.setProperty("key.deserializer",
        "org.apache.kafka.common.serialization.StringDeserializer");
    consumerConf.setProperty("value.deserializer",
        "org.apache.kafka.common.serialization.ByteArrayDeserializer");
    consumerConf.setProperty("metadata.max.age.ms", "100000");//100秒更新配置信息，用以消费新topic。
    GameConsumer consumer = new GameConsumer(consumerConf, records -> {
      for (ConsumerRecord<String, byte[]> record : records) {
        System.out.printf("topics = %s offset = %d, key = %s, value = %s%n", record.topic(),
            record.offset(), record.key(),
            new String(record.value(), 0, record.value().length, StandardCharsets.UTF_8));
      }
    });
    new Thread(consumer.subscribe(Collections.singleton(args[0]))).start();

    Properties props = new Properties();
    props.put("bootstrap.servers", "localhost:9092");
    props.put("acks", "all");
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    props.put("value.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer");

    GameProducer producer = new GameProducer(props);
    Scanner scanner = new Scanner(System.in);
    while (true) {
      String line = scanner.nextLine();
      if (line.equalsIgnoreCase("exists")) {
        break;
      }

      String[] inputs = line.split(" ");
      if (2 <= inputs.length) {
        producer.send(new ProducerRecord<>(inputs[0], inputs[1].getBytes(StandardCharsets.UTF_8)));
      }
    }
    producer.close();
    consumer.close();
  }

}
