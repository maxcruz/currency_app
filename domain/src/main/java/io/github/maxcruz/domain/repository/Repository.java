package io.github.maxcruz.domain.repository;


import io.github.maxcruz.domain.model.ConversionRate;
import io.reactivex.Completable;
import io.reactivex.Observable;

public interface CurrencyData {

    Observable<ConversionRate> retrieveLocalRates();

    Completable saveLocalRate(ConversionRate rate);

    Observable<ConversionRate> retrieveRemoteRates();
}
