package com.example.gateway.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.KafkaListenerAnnotationBeanPostProcessor;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;

/**
 * 1.{@link KafkaListenerAnnotationBeanPostProcessor processKafkaListener}
 *
 * 2.{@link KafkaMessageListenerContainer}
 *
 * 3.{@link KafkaMessageListenerContainer.ListenerConsumer}
 *
 * @author hqc
 * @date 2020/3/30 22:16
 */
public class KafkaConsumer {

    @KafkaListener(topics = "test")
    private void receiveMessage(ConsumerRecord record){

        Object value = record.value();

        System.out.println(value);

    }


}
