package io.github.maxcruz.repository.remote;

import io.github.maxcruz.repository.remote.dto.ExchangeRate;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * CurrencyLayer API mapping
 * https://currencylayer.com/documentation
 */
public interface CurrencyService {

    String URL = "http://www.apilayer.net/api/";

    /**
     * Get specified currency pairs with their respective exchange rate values.
     *
     * @param accessKey A unique "password" provided to access any of the API's data endpoints.
     * @return Exchange rates if the request if successfully, otherwise an error is returned.
     */
    @GET("live")
    Observable<ExchangeRate> getExchangeRate(@Query("access_key") String accessKey);
}
