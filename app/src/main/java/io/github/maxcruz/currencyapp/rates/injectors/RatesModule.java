package io.github.maxcruz.currencyapp.rates.injectors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.github.maxcruz.currencyapp.rates.RatesViewModelFactory;
import io.github.maxcruz.domain.interactors.DownloadRemoteRates;
import io.github.maxcruz.domain.interactors.GetSavedRates;
import io.github.maxcruz.domain.repository.Repository;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Dependency injection for the rates feature
 */
@Module
public class RatesModule {

    @Provides
    @Singleton
    DownloadRemoteRates providesDownloadRemoteRates(Repository repository) {
        return new DownloadRemoteRates(repository, Schedulers.io(), AndroidSchedulers.mainThread());
    }

    @Provides
    @Singleton
    GetSavedRates providesGetSavedRates(Repository repository) {
        return new GetSavedRates(repository, Schedulers.io(), AndroidSchedulers.mainThread());
    }

    @Provides
    @Singleton
    RatesViewModelFactory providesViewModelFactory(DownloadRemoteRates downloadRemoteRates,
                                                   GetSavedRates getSavedRates) {
        return new RatesViewModelFactory(downloadRemoteRates, getSavedRates);
    }
}
