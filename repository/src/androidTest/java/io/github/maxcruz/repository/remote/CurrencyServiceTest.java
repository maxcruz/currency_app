package io.github.maxcruz.repository.remote;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.net.SocketTimeoutException;

import io.github.maxcruz.repository.remote.dto.ExchangeRate;
import io.github.maxcruz.repository.rules.MockWebServerRule;
import io.github.maxcruz.repository.rules.ServiceFactoryRule;
import io.github.maxcruz.repository.utils.RestServiceTestHelper;
import io.reactivex.observers.TestObserver;

import static org.junit.Assert.assertNotNull;

public class CurrencyServiceTest {

    @Rule
    public ServiceFactoryRule serviceFactoryRule = new ServiceFactoryRule();

    @Rule
    public MockWebServerRule mockWebServerRule = new MockWebServerRule();


    @Test
    public void getExchangeRateSuccess() throws IOException {
        // Given
        CurrencyService currencyService = serviceFactoryRule.getServiceFactory()
                .createService(CurrencyService.class, mockWebServerRule.getUrl());
        String body = getBodyFromFile();
        mockWebServerRule.enqueueResponse(body);

        // When
        TestObserver<ExchangeRate> observer = currencyService
                .getExchangeRate().test();
        ExchangeRate exchangeRate = observer.values().get(0);

        // Then
        observer.assertNoErrors();
        assert exchangeRate != null;
        assertNotNull(exchangeRate.getBase());
        assertNotNull(exchangeRate.getRates());
    }

    @Test
    public void getExchangeRateTimeOut() throws IOException {
        // Given
        CurrencyService currencyService = serviceFactoryRule.getServiceFactory()
                .withDebugBodyLogger(true)
                .withTimeOut(1)
                .createService(CurrencyService.class, mockWebServerRule.getUrl());
        String body = getBodyFromFile();
        mockWebServerRule.enqueueThrottleResponse(body, 1);

        // When
        TestObserver<ExchangeRate> observer = currencyService
                .getExchangeRate().test();

        // Then
        observer.assertFailure(SocketTimeoutException.class);
    }

    private String getBodyFromFile() throws IOException {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        return RestServiceTestHelper.getStringFromFile(context, "currency_success.json");
    }
}