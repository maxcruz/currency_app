package io.github.maxcruz.repository.remote.dto;

/**
 *  Error model used by CurrencyLayer to show that something went wrong
 */
@SuppressWarnings("unused")
public class Error {

    private int code;
    private String info;

    /**
     * @return 3-digit error-code
     */
    public int getCode() {
        return code;
    }

    /**
     * @param code Integer error code
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * @return plain text "info" property containing suggestions for the user
     */
    public String getInfo() {
        return info;
    }

    /**
     * @param info text explaining what happened
     */
    public void setInfo(String info) {
        this.info = info;
    }
}
