package io.github.maxcruz.repository.remote;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.List;

import io.github.maxcruz.repository.remote.dto.Country;
import io.github.maxcruz.repository.rules.MockWebServerRule;
import io.github.maxcruz.repository.rules.ServiceFactoryRule;
import io.github.maxcruz.repository.utils.RestServiceTestHelper;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class CountryServiceTest {

    @Rule
    public ServiceFactoryRule serviceFactoryRule = new ServiceFactoryRule();

    @Rule
    public MockWebServerRule mockWebServerRule = new MockWebServerRule();

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void getCountrySuccess() throws IOException {
        // Given
        CountryService countryService = serviceFactoryRule.getServiceFactory()
                .createService(CountryService.class, mockWebServerRule.getUrl());
        String body = getBodyFromFile();
        mockWebServerRule.enqueueResponse(body);

        // When
        Call<List<Country>> request = countryService.getCountry("cop");
        Response<List<Country>> response = request.execute();

        // Then
        assert response.body() != null;
        assertEquals(200, response.code());
        assertNotEquals(0, response.body().size());
        assertNotNull(response.body().get(0).getName());
        assertNotNull(response.body().get(0).getFlag());
        assertNotEquals(0, response.body().get(0).getCurrencies());
        assertNotNull(response.body().get(0).getCurrencies().get(0).getCode());
        assertNotNull(response.body().get(0).getCurrencies().get(0).getName());
        assertNotNull(response.body().get(0).getCurrencies().get(0).getSymbol());
    }

    @Test
    public void getCountryError() throws IOException {
        // Given
        CountryService countryService = serviceFactoryRule.getServiceFactory()
                .createService(CountryService.class, mockWebServerRule.getUrl());
        mockWebServerRule.enqueueNotFound();

        // When
        Call<List<Country>> request = countryService.getCountry("xxx");
        Response<List<Country>> response = request.execute();

        // Then
        assertEquals(404, response.code());
        assertNull(response.body());
    }

    @Test
    public void getCountryTimeOut() throws IOException {
        // Given

        CountryService countryService = new ServiceFactory(new OkHttpClient.Builder(), new HttpLoggingInterceptor())


                .withDebugBodyLogger(true)
                .withTimeOut(1)
                .createService(CountryService.class, mockWebServerRule.getUrl());
        String body = getBodyFromFile();
        mockWebServerRule.enqueueThrottleResponse(body, 1);

        // When
        Call<List<Country>> request = countryService.getCountry("cop");

        // Then
        exception.expect(SocketTimeoutException.class);
        request.execute();
    }

    private String getBodyFromFile() throws IOException {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        return RestServiceTestHelper.getStringFromFile(context, "country_success.json");
    }
}