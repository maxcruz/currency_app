package io.github.maxcruz.currencyapp.rates;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import io.github.maxcruz.domain.interactors.DownloadRemoteRates;
import io.github.maxcruz.domain.interactors.GetSavedRates;
import io.github.maxcruz.domain.model.ConversionRate;
import io.reactivex.Completable;
import io.reactivex.disposables.CompositeDisposable;

/**
 * ViewModel for the rates feature.
 */
public class RatesViewModel extends ViewModel {

    private CompositeDisposable compositeDisposable;
    private DownloadRemoteRates downloadRemoteRates;
    private GetSavedRates getSavedRates;

    private final MutableLiveData<List<ConversionRate>> rates = new MutableLiveData<>();
    private final MutableLiveData<Status> status = new MutableLiveData<>();

    RatesViewModel(DownloadRemoteRates downloadRemoteRates, GetSavedRates getSavedRates) {
        this.downloadRemoteRates = downloadRemoteRates;
        this.getSavedRates = getSavedRates;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    protected void onCleared() {
        compositeDisposable.clear();
    }

    void synchronize() {
        compositeDisposable.add(Completable.fromObservable(downloadRemoteRates.execute())
                .doOnSubscribe(disposable -> status.setValue(Status.LOADING))
                .doFinally(() -> status.setValue(Status.COMPLETE))
                .subscribe(() -> status.setValue(Status.SUCCESS),
                        error -> status.setValue(Status.ERROR)));
    }

    void loadConversionRates() {
        compositeDisposable.add(getSavedRates.execute().toList().subscribe(rates::postValue));
    }

    MutableLiveData<List<ConversionRate>> getRates() {
        return rates;
    }

    MutableLiveData<Status> getStatus() {
        return status;
    }

    public enum Status {
        LOADING,
        SUCCESS,
        ERROR,
        COMPLETE
    }
}