package io.github.maxcruz.domain.interactors;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.github.maxcruz.domain.model.ConversionRate;
import io.github.maxcruz.domain.repository.Repository;
import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class GetSavedRatesTest {

    @Mock
    private Repository currencyRepository;

    private TestScheduler testScheduler;
    private GetSavedRates savedRates;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        testScheduler = new TestScheduler();
        savedRates = new GetSavedRates(currencyRepository, testScheduler, testScheduler);
    }

    @Test
    public void buildUseCaseObservable() {
        // When
        savedRates.buildUseCaseObservable();

        // Then
        verify(currencyRepository).retrieveLocalRates();
        verifyNoMoreInteractions(currencyRepository);
    }

    @Test
    public void retrieveLocalRates() {
        // Given
        ConversionRate rate = new ConversionRate("COP", 3.0);
        when(currencyRepository.retrieveLocalRates()).thenReturn(Observable.just(rate));

        // When
        TestObserver<ConversionRate> observer = savedRates.execute().test();
        testScheduler.triggerActions();

        // Then
        observer.assertResult(rate);
    }
}