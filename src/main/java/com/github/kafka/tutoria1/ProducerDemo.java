package com.github.kafka.tutoria1;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

import static org.apache.kafka.clients.producer.ProducerConfig.*;

public class ProducerDemo {

    public static void main(String[] args) {

        String bootstrapServers = "127.0.0.1:9092";

        // Create Producer Properties
        Properties properties = new Properties();
        // kafka address
        properties.setProperty(BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        //key and value has to be serialized in bytes
        properties.setProperty(KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        properties.setProperty(VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // Create Producer

        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);

        // Create a producer record

        ProducerRecord<String, String> record = new ProducerRecord<String, String>("first_topic", "Hello World");

        // Send Data - Asynchronous
        producer.send(record);

        //Flush data
        producer.flush();

        //Flush and Close producer
        producer.close();

    }
}
