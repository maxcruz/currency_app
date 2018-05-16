package io.github.maxcruz.repository.local.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Data base object to save the currency conversion rate
 */
@Entity(tableName = "conversion_rate")
public class ConversionRate {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "code")
    private String code;

    @ColumnInfo(name = "rate")
    private Double rate;

    /**
     * @return registry ID.
     */
    public int getUid() {
        return uid;
    }

    /**
     * @param uid set identifier.
     */
    public void setUid(int uid) {
        this.uid = uid;
    }

    /**
     * @return ISO code for the currency.
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code set ISO code.
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return conversion rate to the base currency.
     */
    public Double getRate() {
        return rate;
    }

    /**
     * @param rate set the conversion rate to the base currency.
     */
    public void setRate(Double rate) {
        this.rate = rate;
    }
}
