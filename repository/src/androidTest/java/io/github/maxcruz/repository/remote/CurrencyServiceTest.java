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
import retrofit2.Call;
import retrofit2.Response;

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
        Call<ExchangeRate> request = currencyService.getExchangeRate(BuildConfig.CURRENCY_KEY);
        Response<ExchangeRate> response = request.execute();

        // Then
        assert response.body() != null;
        assertEquals(200, response.code());
        assertEquals(true, response.body().isSuccess());
        assertNotNull(response.body().getSource());
        assertNotNull(response.body().getQuotes());
    }

    @Test
    public void getExchangeRateError() throws IOException {
        // Given
        CurrencyService currencyService = serviceFactoryRule.getServiceFactory()
                .createService(CurrencyService.class, mockWebServerRule.getUrl());
        String body = getBodyFromFile("currency_error.json");
        mockWebServerRule.enqueueResponse(body);

        // When
        Call<ExchangeRate> request = currencyService.getExchangeRate(BuildConfig.CURRENCY_KEY);
        Response<ExchangeRate> response = request.execute();

        // Then
        assert response.body() != null;
        assertEquals(200, response.code());
        assertEquals(false, response.body().isSuccess());
        assertNotNull(response.body().getError());
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
        Call<ExchangeRate> request = currencyService.getExchangeRate(BuildConfig.CURRENCY_KEY);

        // Then
        exception.expect(SocketTimeoutException.class);
        request.execute();
    }

    private String getBodyFromFile(String file) throws IOException {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        return RestServiceTestHelper.getStringFromFile(context, file);
    }
}