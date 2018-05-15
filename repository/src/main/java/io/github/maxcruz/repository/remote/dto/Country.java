package io.github.maxcruz.repository.remote.dto;

import java.util.List;

/**
 * Country information model
 */
@SuppressWarnings("unused")
public class Country {

    private String name;
    private String flag;
    private List<Currency> currencies;

    /**
     * @return name of the country
     */
    public String getName() {
        return name;
    }

    /**
     * @param name set name value
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return flag image URL
     */
    public String getFlag() {
        return flag;
    }

    /**
     * @param flag set flag value
     */
    public void setFlag(String flag) {
        this.flag = flag;
    }

    /**
     * @return list of currencies for the country
     */
    public List<Currency> getCurrencies() {
        return currencies;
    }

    /**
     * @param currencies set currencies list
     */
    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
    }
}
