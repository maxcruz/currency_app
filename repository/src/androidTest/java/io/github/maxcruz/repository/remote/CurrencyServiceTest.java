package io.github.maxcruz.repository.remote;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.net.SocketTimeoutException;

import io.github.maxcruz.repository.BuildConfig;
import io.github.maxcruz.repository.remote.dto.ExchangeRate;
import io.github.maxcruz.repository.rules.MockWebServerRule;
import io.github.maxcruz.repository.rules.ServiceFactoryRule;
import io.github.maxcruz.repository.utils.RestServiceTestHelper;
import io.reactivex.observers.TestObserver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CurrencyServiceTest {

    @Rule
    public ServiceFactoryRule serviceFactoryRule = new ServiceFactoryRule();

    @Rule
    public MockWebServerRule mockWebServerRule = new MockWebServerRule();

    @Rule
    public final ExpectedException exception = ExpectedException.none();


    @Test
    public void getExchangeRateSuccess() throws IOException {
        // Given
        CurrencyService currencyService = serviceFactoryRule.getServiceFactory()
                .createService(CurrencyService.class, mockWebServerRule.getUrl());
        String body = getBodyFromFile("currency_success.json");
        mockWebServerRule.enqueueResponse(body);

        // When
        TestObserver<ExchangeRate> observer = currencyService
                .getExchangeRate(BuildConfig.CURRENCY_KEY).test();
        ExchangeRate exchangeRate = observer.values().get(0);

        // Then
        observer.assertNoErrors();
        assert exchangeRate != null;
        assertEquals(true, exchangeRate.isSuccess());
        assertNotNull(exchangeRate.getSource());
        assertNotNull(exchangeRate.getQuotes());
    }

    @Test
    public void getExchangeRateError() throws IOException {
        // Given
        CurrencyService currencyService = serviceFactoryRule.getServiceFactory()
                .createService(CurrencyService.class, mockWebServerRule.getUrl());
        String body = getBodyFromFile("currency_error.json");
        mockWebServerRule.enqueueResponse(body);

        // When
        TestObserver<ExchangeRate> observer = currencyService
                .getExchangeRate(BuildConfig.CURRENCY_KEY).test();
        ExchangeRate exchangeRate = observer.values().get(0);

        // Then
        observer.assertNoErrors();
        assert exchangeRate != null;
        assertEquals(false, exchangeRate.isSuccess());
        assertNotNull(exchangeRate.getError());
    }

    @Test
    public void getExchangeRateTimeOut() throws IOException {
        // Given
        CurrencyService currencyService = serviceFactoryRule.getServiceFactory()
                .withDebugBodyLogger(true)
                .withTimeOut(1)
                .createService(CurrencyService.class, mockWebServerRule.getUrl());
        String body = getBodyFromFile("currency_success.json");
        mockWebServerRule.enqueueThrottleResponse(body, 1);

        // When
        TestObserver<ExchangeRate> observer = currencyService
                .getExchangeRate(BuildConfig.CURRENCY_KEY).test();

        // Then
        observer.assertFailure(SocketTimeoutException.class);
    }

    private String getBodyFromFile(String file) throws IOException {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        return RestServiceTestHelper.getStringFromFile(context, file);
    }
}