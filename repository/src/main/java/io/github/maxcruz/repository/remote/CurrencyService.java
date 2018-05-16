package io.github.maxcruz.repository.remote;

import io.github.maxcruz.repository.remote.dto.ExchangeRate;
import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * CurrencyLayer API mapping
 * https://github.com/fixerAPI/fixer#readme
 */
public interface CurrencyService {

    String URL = "http://api.fixer.io/";

    /**
     * Get specified currency pairs with their respective exchange rate values.
     *
     * @return Exchange rates if the request if successfully.
     */
    @GET("latest?base=USD")
    Observable<ExchangeRate> getExchangeRate();
}
