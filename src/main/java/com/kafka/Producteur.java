package com.kafka;

import org.apache.kafka.clients.producer.*;
import java.util.*;

public class Producteur implements Runnable {
    private int id;
    private int debut;
    private int fin;

    public Producteur(int id, int debut, int fin) {
        this.id = id;
        this.debut = debut;
        this.fin = fin;
    }

    public void run() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, CustomPartitioner.class.getName());

        Producer<String, String> producteur = new KafkaProducer<>(props);

        for (int i = debut; i <= fin; i++) {
            String message = "Le producteur " + id + " a envoyé " + i;
            producteur.send(new ProducerRecord<String, String>("topic1", Integer.toString(i), message));
            System.out.println("Le producteur " + id + " a envoyé " + message);
        }
        producteur.close();
        System.out.println("Producteur " + id + " terminé.");
    }
}
