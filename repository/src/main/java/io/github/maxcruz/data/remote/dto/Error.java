package io.github.maxcruz.data.remote.dto;

/**
 *  Error model used by CurrencyLayer to show that something went wrong
 */
public class Error {

    private int code;
    private String info;

    /**
     * Get error code property
     *
     * @return 3-digit error-code
     */
    public int getCode() {
        return code;
    }

    /**
     * Set error code property
     *
     * @param code Integer error code
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * Get information property
     *
     * @return plain text "info" property containing suggestions for the user
     */
    public String getInfo() {
        return info;
    }

    /**
     * Set information property
     *
     * @param info text explaining what happened
     */
    public void setInfo(String info) {
        this.info = info;
    }
}
