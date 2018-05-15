package io.github.maxcruz.repository.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import io.github.maxcruz.repository.local.entity.ConversionRate;

/**
 * Data access object for conversion rates.
 */
@Dao
public interface ConversionRateDao {

    /**
     * @param base ISO code base.
     * @return a list of conversion rates for the given base.
     */
    @Query("SELECT * FROM conversion_rate WHERE base = :base")
    List<ConversionRate> getAllByBase(String base);

    /**
     * @param code ISO code.
     * @return a conversion rates for the given code.
     */
    @Query("SELECT * FROM conversion_rate WHERE code = :code LIMIT 1")
    ConversionRate getByCode(String code);

    /**
     * @param id ISO code.
     * @return a conversion rates for the given code.
     */
    @Query("SELECT * FROM conversion_rate WHERE uid = :id LIMIT 1")
    ConversionRate getById(int id);

    /**
     * @param base ISO code base.
     * @return number of rates for the base
     */
    @Query("SELECT COUNT(*) from conversion_rate WHERE base = :base")
    int countRatesByBase(String base);

    /**
     * @return number of rates
     */
    @Query("SELECT COUNT(*) from conversion_rate")
    int countAll();

    /**
     * Clear the table conversion_rate
     */
    @Query("DELETE from conversion_rate")
    void clear();

    /**
     * Save the given rates to the database.
     *
     * @param rates rates to save.
     */
    @Insert
    void insertAll(ConversionRate... rates);

    /**
     * Delete the database rate.
     *
     * @param rate Object to remove.
     */
    @Delete
    void delete(ConversionRate rate);
}
