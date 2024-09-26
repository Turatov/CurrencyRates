//package yt.vibe.kafka;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import yt.vibe.entities.Currency;
//
//import javax.persistence.PostPersist;
//import javax.persistence.PostUpdate;
//import javax.persistence.PrePersist;
//import javax.persistence.PreUpdate;
//
//@Component
//@RequiredArgsConstructor
//public class CurrencyListener {
//    @Autowired
//    private CurrencyProducer kafkaProducerService;
//
//    @PostPersist
//    @PostUpdate
//    @PreUpdate
//    @PrePersist
//    public void onPersistOrUpdate(Currency entity) {
//        // Отправляем обновленные данные в Kafka
//        kafkaProducerService.sendMessage(entity);
//    }
//}
