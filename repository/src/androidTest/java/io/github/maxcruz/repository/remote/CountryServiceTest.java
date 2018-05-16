package io.github.maxcruz.repository.remote;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

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
import io.reactivex.observers.TestObserver;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

@SuppressWarnings("unchecked")
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
        TestObserver<List<Country>> observer = countryService.getCountry("cop").test();
        List<Country> list = observer.values().get(0);

        // Then
        observer.assertNoErrors();
        assert list != null;
        assertNotEquals(0, list.size());
        assertNotNull(list.get(0).getName());
        assertNotNull(list.get(0).getFlag());
        assertNotEquals(0, list.get(0).getCurrencies());
        assertNotNull(list.get(0).getCurrencies().get(0).getCode());
        assertNotNull(list.get(0).getCurrencies().get(0).getName());
        assertNotNull(list.get(0).getCurrencies().get(0).getSymbol());
    }

    @Test
    public void getCountryError() {
        // Given
        CountryService countryService = serviceFactoryRule.getServiceFactory()
                .createService(CountryService.class, mockWebServerRule.getUrl());
        mockWebServerRule.enqueueNotFound();

        // When
        TestObserver<List<Country>> observer = countryService.getCountry("xxx").test();

        // Then
        observer.assertFailure(HttpException.class);
    }

    @Test
    public void getCountryTimeOut() throws IOException {
        // Given
        CountryService countryService = serviceFactoryRule.getServiceFactory()
                .withDebugBodyLogger(true)
                .withTimeOut(1)
                .createService(CountryService.class, mockWebServerRule.getUrl());
        String body = getBodyFromFile();
        mockWebServerRule.enqueueThrottleResponse(body, 1);

        // When
        TestObserver<List<Country>> observer = countryService.getCountry("cop").test();

        // Then
        observer.assertFailure(SocketTimeoutException.class);
    }

    private String getBodyFromFile() throws IOException {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        return RestServiceTestHelper.getStringFromFile(context, "country_success.json");
    }
}