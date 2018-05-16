package io.github.maxcruz.repository.remote.dto;

import java.util.Map;

/**
 * Currency service DTO model.
 *
 * If "success" value is true, the "base" and "rates" should be defined,
 * otherwise, "error" will be set.
 */
@SuppressWarnings("unused")
public class ExchangeRate {

    private String base;
    private Map<String, Double> rates;

    public String getBase() {
        return base;
    }

    public void setBase( String base) {
        this.base = base;
    }

    public Map<String, Double> getRates() {
        return rates;
    }

    public void setRates(Map<String, Double> rates) {
        this.rates = rates;
    }
}
