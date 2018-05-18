package io.github.maxcruz.currencyapp.rates.injectors;

import javax.inject.Singleton;

import dagger.Component;
import io.github.maxcruz.currencyapp.rates.RatesFragment;
import io.github.maxcruz.repository.injectors.DatabaseModule;
import io.github.maxcruz.repository.injectors.RepositoryModule;
import io.github.maxcruz.repository.injectors.ServiceModule;

/**
 * Feature component
 */
@Singleton
@Component(modules = {
        RatesModule.class,
        DatabaseModule.class,
        ServiceModule.class,
        RepositoryModule.class
})
public interface RatesComponent {

    void inject(RatesFragment fragment);

}
