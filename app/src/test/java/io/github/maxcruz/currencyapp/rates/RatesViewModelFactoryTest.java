package io.github.maxcruz.currencyapp.rates;

import android.arch.lifecycle.ViewModel;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.matchers.Any;

import io.github.maxcruz.domain.interactors.DownloadRemoteRates;
import io.github.maxcruz.domain.interactors.GetSavedRates;

import static org.junit.Assert.assertTrue;

public class RatesViewModelFactoryTest {

    @Mock
    private DownloadRemoteRates downloadRemoteRates;

    @Mock
    private GetSavedRates getSavedRates;

    private RatesViewModelFactory ratesViewModelFactory;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        ratesViewModelFactory = new RatesViewModelFactory(downloadRemoteRates, getSavedRates);
    }

    @Test
    public void createSuccess() {
        // When
        ViewModel model = ratesViewModelFactory.create(ViewModel.class);

        // Then
        assertTrue(model instanceof RatesViewModel);
    }
}