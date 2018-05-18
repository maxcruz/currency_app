package io.github.maxcruz.currencyapp.rates;

import android.arch.core.executor.testing.InstantTaskExecutorRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.concurrent.TimeoutException;

import io.github.maxcruz.domain.interactors.DownloadRemoteRates;
import io.github.maxcruz.domain.interactors.GetSavedRates;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RatesViewModelTest {

    @Mock
    private DownloadRemoteRates downloadRemoteRates;

    @Mock
    private GetSavedRates getSavedRates;

    @Mock
    private CompositeDisposable compositeDisposable;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private RatesViewModel viewModel;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        viewModel = new RatesViewModel(downloadRemoteRates, getSavedRates, compositeDisposable);
    }

    @Test
    public void onCleared() {
        // When
        viewModel.onCleared();

        // Then
        verify(compositeDisposable).clear();
    }

    @Test
    public void synchronize() {
        // Given
        when(downloadRemoteRates.execute()).thenReturn(Observable.never());

        // When
        viewModel.synchronize();

        // Then
        verify(compositeDisposable).add(any());
        verify(downloadRemoteRates, times(1)).execute();
    }

    @Test
    public void synchronizeWithError() {
        // Given
        when(downloadRemoteRates.execute()).thenReturn(Observable.error(TimeoutException::new));

        // When
        viewModel.synchronize();

        // Then
        verify(compositeDisposable).add(any());
        verify(downloadRemoteRates, times(1)).execute();

    }

    @Test
    public void loadConversionRates() {
        // Given
        when(getSavedRates.execute()).thenReturn(Observable.never());

        // When
        viewModel.loadConversionRates();

        // Then
        verify(compositeDisposable).add(any());
        verify(getSavedRates, times(1)).execute();

    }

    @Test
    public void loadConversionRatesEmpty() {
        // Given
        when(getSavedRates.execute()).thenReturn(Observable.empty());

        // When
        viewModel.loadConversionRates();

        // Then
        verify(compositeDisposable).add(any());
        verify(getSavedRates, times(1)).execute();

    }
}