package io.github.maxcruz.domain.repository;


import java.util.List;

import io.github.maxcruz.domain.model.ConversionRate;
import io.reactivex.Completable;
import io.reactivex.Observable;

public interface CurrencyData {

    Observable<ConversionRate> retrieveLocalRates();

    Completable saveLocalRates(List<ConversionRate> rates);

    Observable<ConversionRate> retrieveRemoteRates();
}
