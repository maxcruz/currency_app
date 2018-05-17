package io.github.maxcruz.domain.interactors;

import io.github.maxcruz.domain.repository.Repository;
import io.reactivex.Observable;
import io.reactivex.Scheduler;

/**
 * Download the list of rates and save them in the local storage
 */
public class DownloadRemoteRates extends UseCase<Void> {

    private Repository repository;

    public DownloadRemoteRates(Repository repository, Scheduler subscribeOn, Scheduler observeOn) {
        super(subscribeOn, observeOn);
        this.repository = repository;
    }

    /**
     * Synchronize the remote information
     *
     * @return observable that emits complete or error
     */
    @Override
    protected Observable<Void> buildUseCaseObservable() {
        return repository.retrieveRemoteRates()
                .flatMap(rate -> repository.saveLocalRate(rate).toObservable());
    }
}
