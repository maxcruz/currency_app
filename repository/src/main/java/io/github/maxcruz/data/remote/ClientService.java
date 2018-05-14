package io.github.maxcruz.data.remote;

import io.github.maxcruz.data.BuildConfig;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Create an implementation of Retrofit to communicate with an service API.
 */
public class ClientService {

    /**
     * This class adapts an interface to HTTP calls.
     *
     * @param clazz Interface mapping the service endpoints using annotations on the declared
     *              methods to define how requests are made.
     * @param url Base URL for the rest API.
     * @see CurrencyService
     * @see CountryService
     * @return An implementation of the given Rest API interface.
     */
    public static <T> T createService(Class<T> clazz , String url) {

        // Increment log details if you are running in debug
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
            logger.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClientBuilder.addInterceptor(logger);
        }

        // Create a Retrofit instance using the provided builder
        Retrofit retrofit = (new Retrofit.Builder())
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClientBuilder.build())
                .build();

        // Return the implementation of the interface
        return retrofit.create(clazz);
    }
}
