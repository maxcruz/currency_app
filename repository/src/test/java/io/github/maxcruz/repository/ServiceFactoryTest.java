package io.github.maxcruz.repository.remote;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.verification.VerificationMode;

import java.util.concurrent.TimeUnit;

import io.github.maxcruz.repository.remote.rules.ServiceFactoryRule;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ServiceFactoryTest {

    @Rule
    public ServiceFactoryRule serviceFactoryRule = new ServiceFactoryRule();

    @Test
    public void shouldCreateCurrencyService() {
        assertThat(serviceFactoryRule.getServiceFactory()
                        .createService(CurrencyService.class,  CurrencyService.URL),
                instanceOf(CurrencyService.class));
    }

    @Test
    public void shouldSetTimeOut() {
        // Given
        int seconds = 5;
        when(serviceFactoryRule.getBuilder().readTimeout(seconds, TimeUnit.SECONDS))
                .thenReturn(serviceFactoryRule.getBuilder());

        // When
        serviceFactoryRule.getServiceFactory().withTimeOut(seconds);

        //Then
        verify(serviceFactoryRule.getBuilder(), times(1))
                .readTimeout(seconds, TimeUnit.SECONDS);
    }

    @Test
    public void shouldAddBodyLogger() {
        setBodyLogger(true, times(1));
    }

    @Test
    public void shouldNotAddBodyLogger() {
        setBodyLogger(false, never());
    }

    private void setBodyLogger(final boolean debug, VerificationMode mode) {
        // When
        serviceFactoryRule.getServiceFactory().withDebugBodyLogger(debug);

        // Then
        verify(serviceFactoryRule.getBuilder(), mode)
                .addInterceptor(serviceFactoryRule.getInterceptor());
    }
}