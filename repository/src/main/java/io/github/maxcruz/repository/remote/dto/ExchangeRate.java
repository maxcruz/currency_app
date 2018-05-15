package io.github.maxcruz.repository.remote.dto;

import android.support.annotation.Nullable;

import java.util.Map;

/**
 * Exchange Rate values, consisting of the currency pairs and their respective Conversion rates.
 *
 * If "success" value is true, the "source" and "quotes" should be defined,
 * otherwise, "error" will be set.
 */
@SuppressWarnings("unused")
public class ExchangeRate {

    private Boolean success;
    @Nullable
    private String source;
    @Nullable
    private Map<String, Double> quotes;
    @Nullable
    private Error error;

    /**
     * @return true if your query succeeds, and false, if not.
     */
    public Boolean isSuccess() {
        return success;
    }

    /**
     * @param success set query status
     */
    public void setSuccess(Boolean success) {
        this.success = success;
    }

    /**
     * @return currency base for the conversion.
     */
    @Nullable
    public String getSource() {
        return source;
    }

    /**
     * @param source set currency base.
     */
    public void setSource(@Nullable String source) {
        this.source = source;
    }

    /**
     * @return map with the currencies rates conversion.
     */
    @Nullable
    public Map<String, Double> getQuotes() {
        return quotes;
    }

    /**
     * @param quotes set currencies values
     */
    public void setQuotes(@Nullable Map<String, Double> quotes) {
        this.quotes = quotes;
    }

    /**
     * @return error description when happens
     */
    @Nullable
    public Error getError() {
        return error;
    }

    /**
     * @param error set error value
     */
    public void setError(@Nullable Error error) {
        this.error = error;
    }
}
