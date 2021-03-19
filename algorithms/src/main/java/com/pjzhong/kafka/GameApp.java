package com.pjzhong.kafka;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;

public class GameApp {

  public static void main(String[] args) {
    if (args.length != 1) {
      throw new RuntimeException("required A topic");
    }

    KafkaSession kafkaSession = new KafkaSession(args[0], records -> {
      for (ConsumerRecord<String, byte[]> record : records) {
        System.out.printf("topics = %s offset = %d, key = %s, value = %s%n", record.topic(),
            record.offset(), record.key(),
            new String(record.value(), 0, record.value().length, StandardCharsets.UTF_8));
      }
    });
    new Thread(kafkaSession).start();

    Scanner scanner = new Scanner(System.in);
    while (true) {
      String line = scanner.nextLine();
      if (line.equalsIgnoreCase("exists")) {
        break;
      }

      String[] inputs = line.split(" ");
      if (2 <= inputs.length) {
        kafkaSession.send(new ProducerRecord<>(inputs[0], inputs[1].getBytes(StandardCharsets.UTF_8)));
      }
    }
    kafkaSession.close();
  }

}
