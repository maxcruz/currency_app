package io.github.maxcruz.domain.interactors;

import io.github.maxcruz.domain.model.ConversionRate;
import io.github.maxcruz.domain.repository.Repository;
import io.reactivex.Observable;
import io.reactivex.Scheduler;

/**
 * Retrieve the rates from the local storage.
 */
public class GetSavedRates extends UseCase<ConversionRate> {

    private Repository repository;

    GetSavedRates(Repository repository, Scheduler subscribeOn, Scheduler observeOn) {
        super(subscribeOn, observeOn);
        this.repository = repository;
    }

    /**
     * Return the rates saved in the database.
     *
     * @return observable that emits each saved rate.
     */
    @Override
    protected Observable<ConversionRate> buildUseCaseObservable() {
        return repository.retrieveLocalRates();
    }
}
