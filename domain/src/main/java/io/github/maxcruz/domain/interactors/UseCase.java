package io.github.maxcruz.domain.interactors;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

public abstract class UseCase<T> {

    private final Scheduler subscribeOn;
    private final Scheduler observeOn;

    UseCase(Scheduler subscribeOn, Scheduler observeOn) {
        this.subscribeOn = subscribeOn;
        this.observeOn = observeOn;
    }

    protected abstract Observable<T> buildUseCaseObservable();

    /**
     * Method to execute the use case in a different thread
     */
    Observable<T> execute() {
        return this.buildUseCaseObservable().subscribeOn(subscribeOn).observeOn(observeOn);
    }
}