package io.github.maxcruz.repository.injectors;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.github.maxcruz.repository.local.ConversionRateDao;
import io.github.maxcruz.repository.local.RatesDatabase;

/**
 * Module for dependency injection
 */
@Module
public class DatabaseModule {

    private final Context context;

    public DatabaseModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    RatesDatabase providesRatesDatabase() {
        return RatesDatabase.getDatabase(context);
    }

    @Provides
    @Singleton
    ConversionRateDao providesConversionRateDao(RatesDatabase database) {
        return database.getConversionRateDao();
    }
}
