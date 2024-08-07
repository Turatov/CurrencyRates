package yt.vibe.contoller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import yt.vibe.Currency;
import yt.vibe.dto.CurrencyAddingRequest;
import yt.vibe.service.CurrencyService;
import yt.vibe.service.FreeCurrencyApiService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(MockitoExtension.class)
@WebMvcTest(CurrencyController.class)
class CurrencyControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;


    @MockBean
    private CurrencyService currencyService;

    @MockBean
    private FreeCurrencyApiService freeCurrencyApi;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void itShouldAddCurrency() throws Exception {
        // Given
        Currency currency = new Currency("EUR", 100.1);
        CurrencyAddingRequest request = new CurrencyAddingRequest(currency.getCode(), currency.getRate());
        String writtenValueAsString = objectMapper.writeValueAsString(request);
        mockMvc.perform(post("/api")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(writtenValueAsString))
                .andExpect(status().isCreated());
        verify(currencyService, times(1)).addCurrency((request));
    }

    @Test
    void itShouldGetAllCurrencies() throws Exception {
        // Given
        List<Currency> currencyList = new ArrayList<>();
        currencyList.add(new Currency("UER", 102.2));
        currencyList.add(new Currency("USD", 98.1));
        currencyList.add(new Currency("GBP", 112.5));
        // When
        when(currencyService.getAllCurrencies()).thenReturn(currencyList);
        // Then
        mockMvc.perform(get("/api"))
                .andExpect(status().isOk())
                .andExpect((content().json(objectMapper.writeValueAsString(currencyList))));
        verify(currencyService, times(1)).getAllCurrencies();
    }

    @Test
    void itShouldGetCurrencyByCode() throws Exception {
        // Given
        Currency currency = new Currency("KGS", 10.1);
        // When
        when((currencyService).getCurrencyByCode("KGS")).thenReturn(currency);
        mockMvc.perform(get("/api/{code}", "KGS"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(currency)));
        // Then
        verify(currencyService, times(1)).getCurrencyByCode("KGS");
    }
}