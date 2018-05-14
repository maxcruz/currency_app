package io.github.maxcruz.data.remote;

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
        throw new UnsupportedOperationException();
    }
}
