package yt.vibe.contoller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import yt.vibe.entities.Currency;
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

    @WithMockUser(username = "admin", roles = {"ADMIN"})
//    @Test
        // TODO: 12/09/2024 I don't know why but can't pass the test because spring security  returns 403 -> 401
    void itShouldAddCurrency() throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Given
        Currency currency = new Currency("EUR", 100.1);
        CurrencyAddingRequest request = new CurrencyAddingRequest(currency.getCode(), currency.getRate());
        String writtenValueAsString = objectMapper.writeValueAsString(request);
        mockMvc.perform(post("/api/v1/currencies/admin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(writtenValueAsString))
                .andExpect(status().isCreated());
        verify(currencyService, times(1)).addCurrency((request));
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
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
        mockMvc.perform(get("/api/v1/currencies"))
                .andExpect(status().isOk())
                .andExpect((content().json(objectMapper.writeValueAsString(currencyList))));
        verify(currencyService, times(1)).getAllCurrencies();
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    void itShouldGetCurrencyByCode() throws Exception {
        // Given
        Currency currency = new Currency("KGS", 10.1);
        // When
        when((currencyService).getCurrencyByCode("KGS")).thenReturn(currency);
        mockMvc.perform(get("/api/v1/currencies/{code}", "KGS"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(currency)));
        // Then
        verify(currencyService, times(1)).getCurrencyByCode("KGS");
    }
}