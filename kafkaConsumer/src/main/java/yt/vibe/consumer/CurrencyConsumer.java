package yt.vibe.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CurrencyConsumer {
        @KafkaListener(topics = "debezium_postgres.public.currency" , groupId = "currency-group")
    public void consumeMsg(String msg){
        log.info(String.format("Consuming the message from yt Topic :: %s", msg));
    }
}
