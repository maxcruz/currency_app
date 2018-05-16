package io.github.maxcruz.repository.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import io.github.maxcruz.repository.local.entity.ConversionRateTable;

/**
 * Data access object for conversion rates.
 */
@Dao
public interface ConversionRateDao {

    /**
     * @return a list of conversion rates.
     */
    @Query("SELECT * FROM conversion_rate")
    List<ConversionRateTable> getAll();

    /**
     * @param code ISO code.
     * @return a conversion rates for the given code.
     */
    @Query("SELECT * FROM conversion_rate WHERE code = :code LIMIT 1")
    ConversionRateTable getByCode(String code);

    /**
     * @param id ISO code.
     * @return a conversion rates for the given code.
     */
    @Query("SELECT * FROM conversion_rate WHERE uid = :id LIMIT 1")
    ConversionRateTable getById(int id);

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
    void insertAll(ConversionRateTable... rates);

    /**
     * Delete the database rate.
     *
     * @param rate Object to remove.
     */
    @Delete
    void delete(ConversionRateTable rate);
}
