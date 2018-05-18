package io.github.maxcruz.repository.injectors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.github.maxcruz.repository.BuildConfig;
import io.github.maxcruz.repository.remote.CurrencyService;
import io.github.maxcruz.repository.remote.ServiceFactory;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Dependency injection for service
 */
@Module
public class ServiceModule {

    @Provides
    @Singleton
    OkHttpClient.Builder providesHttpClientBuilder() {
        return new OkHttpClient.Builder();
    }

    @Provides
    @Singleton
    HttpLoggingInterceptor providesHttpLoggingInterceptor() {
        return new HttpLoggingInterceptor();
    }

    @Provides
    @Singleton
    ServiceFactory providesServiceFactory(OkHttpClient.Builder builder,
                                          HttpLoggingInterceptor interceptor) {
        ServiceFactory serviceFactory = new ServiceFactory(builder, interceptor);
        serviceFactory.withDebugBodyLogger(BuildConfig.DEBUG);
        return serviceFactory;
    }

    @Provides
    @Singleton
    CurrencyService providesCurrencyService(ServiceFactory serviceFactory) {
        return serviceFactory.createService(CurrencyService.class, CurrencyService.URL);
    }
}
