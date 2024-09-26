//package yt.vibe.kafka;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.kafka.support.KafkaHeaders;
//import org.springframework.messaging.support.MessageBuilder;
//import org.springframework.stereotype.Service;
//import yt.vibe.entities.Currency;
//import org.springframework.messaging.Message;
//
//@Service
//@RequiredArgsConstructor
//public class CurrencyProducer {
//    private final KafkaTemplate<String, Currency> kafkaTemplate;
//
//    public void sendMessage(Currency student){
//        Message<Currency> message = MessageBuilder
//                .withPayload(student)
//                .setHeader(KafkaHeaders.TOPIC, "yt")
//                .build();
//        kafkaTemplate.send(message);
//    }
//}
