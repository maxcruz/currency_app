package io.github.maxcruz.domain.model;

/**
 * Consisting of the currency pairs and their respective Conversion rates
 */
public class ConversionRate {

    private String code;
    private Double rate;

    public ConversionRate(String code, Double rate) {
        this.code = code;
        this.rate = rate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }
}
