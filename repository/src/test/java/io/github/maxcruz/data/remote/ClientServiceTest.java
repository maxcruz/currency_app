package io.github.maxcruz.data.remote;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

public class ClientServiceTest {

    @Test
    public void shouldCreateCurrencyService() {
        assertThat(ClientService.createService(CurrencyService.class,  CurrencyService.URL),
                instanceOf(CurrencyService.class));
    }
}