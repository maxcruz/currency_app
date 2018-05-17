package io.github.maxcruz.repository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import io.github.maxcruz.domain.model.ConversionRate;
import io.github.maxcruz.repository.local.ConversionRateDao;
import io.github.maxcruz.repository.local.entity.ConversionRateTable;
import io.github.maxcruz.repository.remote.CurrencyService;
import io.github.maxcruz.repository.remote.dto.ExchangeRate;
import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static org.mockito.Mockito.when;

public class CurrencyRepositoryTest {

    private CurrencyRepository repository;

    @Mock
    private ConversionRateDao conversionRateDao;

    @Mock
    private CurrencyService currencyService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        repository = new CurrencyRepository(conversionRateDao, currencyService);
    }

    @Test
    public void retrieveLocalRates() {
        // Given
        List<ConversionRateTable> list = new ArrayList<>();
        list.add(getTableEntry("AUD", 1.3344));
        list.add(getTableEntry("BGN", 1.6459));
        list.add(getTableEntry("BRL", 3.6556));
        when(conversionRateDao.getAll()).thenReturn(list);

        // When
        TestObserver<ConversionRate> observer = repository.retrieveLocalRates().test();

        // Then
        observer.assertNoErrors();
        observer.assertComplete();
        observer.assertValueCount(list.size());
        observer.assertValueAt(1, o ->
                o != null && o.getCode().equals(list.get(1).getCode()));
    }

    @Test
    public void retrieveLocalRatesEmpty() {
        // Given
        List<ConversionRateTable> list = new ArrayList<>();
        when(conversionRateDao.getAll()).thenReturn(list);

        // When
        TestObserver<ConversionRate> observer = repository.retrieveLocalRates().test();

        // Then
        observer.assertNoErrors();
        observer.assertComplete();
    }

    @Test
    public void addLocalRates() {
        // Given
        ConversionRate rate = new ConversionRate("AUD", 1.3344);

        // When
        TestObserver<Void> observer = repository.saveLocalRate(rate).test();

        // Then
        observer.assertComplete();
    }

    @Test
    public void retrieveRemoteRatesSuccess() {
        // Given
        Map<String, Double> rates = new HashMap<>();
        rates.put("AUD", 1.3344);
        rates.put("BGN", 1.6459);
        rates.put("BRL", 3.6556);
        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setBase("USD");
        exchangeRate.setRates(rates);
        when(currencyService.getExchangeRate()).thenReturn(Observable.just(exchangeRate));

        // When
        TestObserver<ConversionRate> observer = repository.retrieveRemoteRates().test();

        // Then
        observer.assertNoErrors();
        observer.assertValueCount(rates.size());
    }

    @Test
    public void retrieveRemoteRatesError() {
        // Given
        when(currencyService.getExchangeRate())
                .thenReturn(Observable.error(new TimeoutException()));

        // When
        TestObserver<ConversionRate> observer = repository.retrieveRemoteRates().test();

        // Then
        observer.assertError(TimeoutException.class);
    }

    private ConversionRateTable getTableEntry(String code, Double rate) {
        ConversionRateTable entry = new ConversionRateTable();
        entry.setCode(code);
        entry.setRate(rate);
        return entry;
    }
}