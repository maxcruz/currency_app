package io.github.maxcruz.domain.interactors;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

/**
 * This abstract class represents an execution unit for different use cases. Any use case in the
 * application should implement this contract.
 *
 * @param <T> type returned by the use case
 */
public abstract class UseCase<T> {

    private final Scheduler subscribeOn;
    private final Scheduler observeOn;

    UseCase(Scheduler subscribeOn, Scheduler observeOn) {
        this.subscribeOn = subscribeOn;
        this.observeOn = observeOn;
    }

    /**
     * Abstract method to implement the use case.
     *
     * @return observable that emits the result.
     */
    protected abstract Observable<T> buildUseCaseObservable();

    /**
     * Method to execute the use case in a different thread
     */
    Observable<T> execute() {
        return this.buildUseCaseObservable().subscribeOn(subscribeOn).observeOn(observeOn);
    }
}