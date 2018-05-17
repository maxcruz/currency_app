package io.github.maxcruz.domain.interactors;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.concurrent.TimeoutException;

import io.github.maxcruz.domain.model.ConversionRate;
import io.github.maxcruz.domain.repository.Repository;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class DownloadRemoteRatesTest {

    @Mock
    private Repository currencyRepository;

    private TestScheduler testScheduler;
    private DownloadRemoteRates remoteRates;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        testScheduler = new TestScheduler();
        remoteRates = new DownloadRemoteRates(currencyRepository, testScheduler, testScheduler);
    }

    @Test
    public void buildUseCaseObservable() {
        // Given
        ConversionRate rate = new ConversionRate("COP", 3.0);
        when(currencyRepository.retrieveRemoteRates()).thenReturn(Observable.just(rate));

        // When
        remoteRates.buildUseCaseObservable();

        // Then
        verify(currencyRepository).retrieveRemoteRates();
        verifyNoMoreInteractions(currencyRepository);
    }

    @Test
    public void downloadAndSave() {
        // Given
        ConversionRate rate = new ConversionRate("COP", 3.0);
        when(currencyRepository.retrieveRemoteRates()).thenReturn(Observable.just(rate));
        when(currencyRepository.saveLocalRate(rate)).thenReturn(Completable.complete());

        // When
        TestObserver observer = remoteRates.execute().test();
        testScheduler.triggerActions();

        verify(currencyRepository).retrieveRemoteRates();
        verify(currencyRepository).saveLocalRate(any());
        observer.assertComplete();
    }

    @Test
    public void downloadError() {
        // Given
        when(currencyRepository.retrieveRemoteRates())
                .thenReturn(Observable.error(new TimeoutException()));

        // When
        TestObserver observer = remoteRates.execute().test();
        testScheduler.triggerActions();

        // Then
        observer.assertError(TimeoutException.class);
    }
}