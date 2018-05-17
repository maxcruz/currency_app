package io.github.maxcruz.currencyapp.rates;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import io.github.maxcruz.domain.interactors.DownloadRemoteRates;
import io.github.maxcruz.domain.interactors.GetSavedRates;
import io.github.maxcruz.domain.model.ConversionRate;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class RatesViewModel extends ViewModel {

    private CompositeDisposable compositeDisposable;
    private DownloadRemoteRates downloadRemoteRates;
    private GetSavedRates getSavedRates;

    private LiveData<List<ConversionRate>> conversionRates;


    RatesViewModel(DownloadRemoteRates downloadRemoteRates, GetSavedRates getSavedRates) {
        this.downloadRemoteRates = downloadRemoteRates;
        this.getSavedRates = getSavedRates;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    protected void onCleared() {
        compositeDisposable.clear();
    }

    public void synchronize() {
        downloadRemoteRates.execute().subscribe();
    }

    private void loadConversionRates() {
        Disposable disposable = getSavedRates.execute().subscribe(
                conversionRate -> conversionRates.getValue().add(conversionRate),
                throwable -> throwable.printStackTrace());
        compositeDisposable.add(disposable);
    }

    public LiveData<List<ConversionRate>> getConversionRates() {
        if (conversionRates == null) {
            conversionRates = new MutableLiveData<>();
            loadConversionRates();
        }
        return conversionRates;
    }
}
