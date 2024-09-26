package yt.vibe.telegram;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import yt.vibe.representation.CurrencyChangeEvent;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TelegramApiService {
    @Value("${telegram.bot.token}")
    private String botToken;
    @Value("${telegram.chat.id}")
    private String chat_id;
    @Value("${telegram.api.url}")
    private String telegramUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public void sendMessage(CurrencyChangeEvent event) {
        String url = telegramUrl + botToken + "/sendMessage";
        Map<String, String> params = new HashMap<>();
        params.put("chat_id", chat_id);
        params.put("text", buildTelegramMessage(event));
        restTemplate.postForObject(url, params, String.class);
    }

    private String buildTelegramMessage(CurrencyChangeEvent event){
        StringBuffer sb = new StringBuffer();
        if (event.getBefore() != null) {
            sb.append(String.format("Курс \"%s\" поменялся \n", event.getBefore().getCode()));
            sb.append("До изменения : ").append(event.getBefore().getRate()).append("\n");
            sb.append("Сейчас : ").append(event.getAfter().getRate()).append("\n");
        }else {
            sb.append(String.format("Добавлена новая валюта \"%s\" курс к доллару [USD]: %s " ,event.getAfter().getCode(),event.getAfter().getRate()));
        }
        return sb.toString();
    }
}
