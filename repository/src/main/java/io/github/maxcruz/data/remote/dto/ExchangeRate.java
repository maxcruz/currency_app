package io.github.maxcruz.data.remote.dto;

import android.support.annotation.Nullable;

import java.util.Map;

/**
 * Exchange Rate values, consisting of the currency pairs and their respective Conversion rates.
 *
 * If "success" value is true, the "source" and "quotes" should be defined,
 * otherwise, "error" will be set.
 */
public class ExchangeRate {

    private Boolean success;
    @Nullable
    private String source;
    @Nullable
    private Map<String, Integer> quotes;
    @Nullable
    private Error error;

    /**
     *
     * @return true if your query succeeds, and false, if not.
     */
    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    @Nullable
    public String getSource() {
        return source;
    }

    public void setSource(@Nullable String source) {
        this.source = source;
    }

    @Nullable
    public Map<String, Integer> getQuotes() {
        return quotes;
    }

    public void setQuotes(@Nullable Map<String, Integer> quotes) {
        this.quotes = quotes;
    }

    @Nullable
    public Error getError() {
        return error;
    }

    public void setError(@Nullable Error error) {
        this.error = error;
    }
}
