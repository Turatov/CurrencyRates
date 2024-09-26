package yt.vibe.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import yt.vibe.representation.CurrencyChangeEvent;
import yt.vibe.telegram.TelegramApiService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@Slf4j
public class CurrencyConsumer {

    @Autowired
    TelegramApiService telegramApiService;
    @KafkaListener(topics = "debezium_postgres.public.currency", groupId = "currency-group")
    public void listen(CurrencyChangeEvent event) {
            telegramApiService.sendMessage(event);
    }
}
