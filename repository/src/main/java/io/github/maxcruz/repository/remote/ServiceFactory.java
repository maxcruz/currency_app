package io.github.maxcruz.repository.remote;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Create an implementation of Retrofit to communicate with an service API.
 */
public class ServiceFactory {

    private OkHttpClient.Builder httpClientBuilder;
    private HttpLoggingInterceptor loggingInterceptor;

    public ServiceFactory(OkHttpClient.Builder httpClientBuilder,
                          HttpLoggingInterceptor loggingInterceptor) {
        this.httpClientBuilder = httpClientBuilder;
        this.loggingInterceptor = loggingInterceptor;
    }

    /**
     * Increment log details if you are running in debug
     *
     * @return class instance
     */
    ServiceFactory withDebugBodyLogger(boolean debug) {
        if (debug) {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClientBuilder.addInterceptor(loggingInterceptor);
        }
        return this;
    }

    /**
     * Add timeout to each request
     *
     * @param seconds time to delay the request
     * @return class instance
     */
    ServiceFactory withTimeOut(int seconds) {
        httpClientBuilder.connectTimeout(seconds, TimeUnit.SECONDS);
        httpClientBuilder.readTimeout(seconds, TimeUnit.SECONDS);
        return this;
    }

    /**
     * This class adapts an interface to HTTP calls.
     *
     * @param clazz Interface mapping the service endpoints using annotations on the declared
     *              methods to define how requests are made.
     * @param url Base URL for the rest API.
     * @return An implementation of the given Rest API interface.
     */
    <T> T createService(Class<T> clazz, String url) {
        // Create a Retrofit instance using the provided builder
        Retrofit retrofit = (new Retrofit.Builder())
                .baseUrl(url)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClientBuilder.build())
                .build();

        // Return the implementation of the interface
        return retrofit.create(clazz);
    }
}
