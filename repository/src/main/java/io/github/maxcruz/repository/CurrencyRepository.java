package io.github.maxcruz.repository;

import io.github.maxcruz.domain.model.ConversionRate;
import io.github.maxcruz.domain.repository.Repository;
import io.github.maxcruz.repository.local.ConversionRateDao;
import io.github.maxcruz.repository.local.entity.ConversionRateTable;
import io.github.maxcruz.repository.remote.CurrencyService;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Repository pattern implementation to access local and remote data
 */
public class CurrencyRepository implements Repository {

    private ConversionRateDao conversionRateDao;
    private CurrencyService currencyService;

    public CurrencyRepository(ConversionRateDao conversionRateDao, CurrencyService currencyService) {
        this.conversionRateDao = conversionRateDao;
        this.currencyService = currencyService;
    }

    @Override
    public Observable<ConversionRate> retrieveLocalRates() {
        return Observable.defer(() -> Observable.fromIterable(conversionRateDao.getAll()))
                .subscribeOn(Schedulers.io())
                .map(tableEntry -> new ConversionRate(tableEntry.getCode(), tableEntry.getRate()));
    }

    @Override
    public Completable saveLocalRate(ConversionRate rate) {
        return Completable.fromAction(() -> {
            ConversionRateTable previous = conversionRateDao.getByCode(rate.getCode());
            if (previous != null) {
                conversionRateDao.delete(previous);
            }
            ConversionRateTable entry = new ConversionRateTable();
            entry.setCode(rate.getCode());
            entry.setRate(rate.getRate());
            conversionRateDao.insertAll(entry);
        });
    }

    @Override
    public Observable<ConversionRate> retrieveRemoteRates() {
        return currencyService.getExchangeRate().flatMap(exchangeRate ->
                Observable.fromIterable(exchangeRate.getRates().entrySet())
                        .map(entry -> new ConversionRate(entry.getKey(), entry.getValue())));
    }
}