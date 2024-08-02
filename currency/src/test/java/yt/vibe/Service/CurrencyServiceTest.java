package yt.vibe.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import yt.vibe.Currency;
import yt.vibe.CurrencyAddingRequest;
import yt.vibe.CurrencyRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.LIST;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class CurrencyServiceTest {

    @Captor
    ArgumentCaptor<Currency> currencyArgumentCaptor;

    @InjectMocks
    CurrencyService underTest;

    @Mock
    CurrencyRepository currencyRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new CurrencyService(currencyRepository);
    }

    @Test
    void itShouldAddCurrency() {
        // Given
        Currency currency = new Currency("USD", 20.2);
        CurrencyAddingRequest currencyAddingRequest = new CurrencyAddingRequest(currency.getCode(), currency.getRate());
        // When
        underTest.addCurrency(currencyAddingRequest);
        // Then
        then(currencyRepository).should().save(currencyArgumentCaptor.capture());
        Currency currencyArgumentCaptorValue = currencyArgumentCaptor.getValue();
        assertThat(currencyArgumentCaptorValue).isEqualTo(currency);
    }

    @Test
    void itShouldShouldNotAddCurrencyThrowException() {
        // Given
        Currency currency = new Currency("USD", 20.2);
        CurrencyAddingRequest currencyAddingRequest = new CurrencyAddingRequest(currency.getCode(), currency.getRate());
        given(currencyRepository.findByCode(currencyAddingRequest.getCurrency().getCode())).willReturn(Optional.of(currency));
        // When
        // Then
        assertThatThrownBy(() -> underTest.addCurrency(currencyAddingRequest))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining(String.format("Currency with this code [%s] is already exist", currencyAddingRequest.getCurrency().getCode()));

    }

    @Test
    void itShouldGetAllCurrencies() {
        // Given
        List<Currency> currencyList = new ArrayList<>();
        currencyList.add(new Currency("EUR", 102.1));
        currencyList.add(new Currency("USD", 90.1));
        currencyList.add(new Currency("RUB", 1.0));
        currencyList.add(new Currency("GBP", 112.1));

        given(currencyRepository.findAll()).willReturn(currencyList);
        // When
        // Then
        Iterable<Currency> allCurrencies = underTest.getAllCurrencies();
        assertThat(allCurrencies).isEqualTo(currencyList);
    }


}
