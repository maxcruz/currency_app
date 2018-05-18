package io.github.maxcruz.currencyapp.rates;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import io.github.maxcruz.domain.interactors.DownloadRemoteRates;
import io.github.maxcruz.domain.interactors.GetSavedRates;

/**
 * As our ViewModel requires customs parameters it is necessary a custom provider.
 */
public class RatesViewModelFactory implements ViewModelProvider.Factory {

    private final DownloadRemoteRates downloadRemoteRates;
    private final GetSavedRates getSavedRates;

    public RatesViewModelFactory(DownloadRemoteRates downloadRemoteRates,
                          GetSavedRates getSavedRates) {
        this.downloadRemoteRates = downloadRemoteRates;
        this.getSavedRates = getSavedRates;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(RatesViewModel.class)) {
            return (T) new RatesViewModel(downloadRemoteRates, getSavedRates);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}