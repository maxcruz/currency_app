package io.github.maxcruz.repository.local.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "conversion_rate")
public class ConversionRate {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "code")
    private String code;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "base")
    private String base;

    @ColumnInfo(name = "flag")
    private String flag;

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
     * @return title name in english.
     */
    public String getName() {
        return name;
    }

    /**
     * @param name set a name for the currency.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return URL to the country flag of the currency.
     */
    public String getFlag() {
        return flag;
    }

    /**
     * @param flag set an image URL for the currency.
     */
    public void setFlag(String flag) {
        this.flag = flag;
    }

    /**
     * @return code for the currency base for the conversion rate.
     */
    public String getBase() {
        return base;
    }

    /**
     * @param base set the baser for the conversion rate.
     */
    public void setBase(String base) {
        this.base = base;
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
