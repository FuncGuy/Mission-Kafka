package com.github.kafka.tutoria1;


import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

import static org.apache.kafka.clients.producer.ProducerConfig.*;

public class ProducerDemoWithCallback {

    public static void main(String[] args) {

        final Logger logger = LoggerFactory.getLogger(ProducerDemoWithCallback.class);

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

        for(int i =0 ;i<10; i++) {


            ProducerRecord<String, String> record = new ProducerRecord<String, String>("first_topic", "Hello World" + Integer.valueOf(i));

            // Send Data - Asynchronous
            producer.send(record, new Callback() {
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    // executes every time a record is successfully sent or an exception is thrown
                    if (e == null) {
                        // the record was successfully sent
                        logger.info("Recieved new metadata. \n " +
                                "TOPIC: " + recordMetadata.topic() + "\n" +
                                "PARTITION: " + recordMetadata.partition() + "\n" +
                                "OFFSET: " + recordMetadata.offset() + "\n" +
                                "TIMESTAMP: " + recordMetadata.timestamp());
                    } else {
                        logger.error("Error while publishing " + e);
                    }
                }
            });
        }

        //Flush data
        producer.flush();

        //Flush and Close producer
        producer.close();

    }
}

