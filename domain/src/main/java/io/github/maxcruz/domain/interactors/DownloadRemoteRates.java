package io.github.maxcruz.domain.interactors;

import io.github.maxcruz.domain.repository.Repository;
import io.reactivex.Observable;
import io.reactivex.Scheduler;

public class DownloadRemoteRates extends UseCase<Void> {

    private Repository repository;

    DownloadRemoteRates(Repository repository, Scheduler subscribeOn, Scheduler observeOn) {
        super(subscribeOn, observeOn);
        this.repository = repository;
    }

    @Override
    protected Observable<Void> buildUseCaseObservable() {
        return repository.retrieveRemoteRates()
                .flatMap(rate -> repository.saveLocalRate(rate).toObservable());
    }
}
