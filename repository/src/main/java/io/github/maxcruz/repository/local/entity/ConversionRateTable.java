package io.github.maxcruz.repository.local.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Data base object to save the currency conversion rate
 */
@Entity(tableName = "conversion_rate")
public class ConversionRateTable {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "code")
    private String code;

    @ColumnInfo(name = "rate")
    private Double rate;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
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
