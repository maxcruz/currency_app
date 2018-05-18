package io.github.maxcruz.currencyapp;

import android.app.Application;
import android.content.Context;

import io.github.maxcruz.currencyapp.rates.injectors.DaggerRatesComponent;
import io.github.maxcruz.currencyapp.rates.injectors.RatesComponent;
import io.github.maxcruz.currencyapp.rates.injectors.RatesModule;
import io.github.maxcruz.repository.injectors.DatabaseModule;
import io.github.maxcruz.repository.injectors.RepositoryModule;
import io.github.maxcruz.repository.injectors.ServiceModule;

public class CurrencyApp extends Application {

    public RatesComponent getRatesComponent(Context context) {
        return DaggerRatesComponent
                .builder()
                .databaseModule(new DatabaseModule(context))
                .serviceModule(new ServiceModule())
                .repositoryModule(new RepositoryModule())
                .ratesModule(new RatesModule())
                .build();
    }

}
