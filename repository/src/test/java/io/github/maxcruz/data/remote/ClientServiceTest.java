package io.github.maxcruz.data.remote;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

public class ClientServiceTest {

    @Test
    public void shouldCreateCurrencyService() {
        assertThat(ClientService.createService(CurrencyService.class,  CurrencyService.URL),
                instanceOf(CurrencyService.class));
    }

    @Test
    public void shouldCreateCountryService() {
        assertThat(ClientService.createService(CountryService.class, CountryService.URL),
                instanceOf(CountryService.class));
    }
}