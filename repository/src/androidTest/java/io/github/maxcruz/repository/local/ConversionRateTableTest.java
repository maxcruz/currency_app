package io.github.maxcruz.repository.local;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import io.github.maxcruz.repository.local.entity.ConversionRateTable;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class ConversionRateTableTest {

    private RatesDatabase ratesDatabase;
    private ConversionRateDao conversionRateDao;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getContext();
        ratesDatabase = Room.inMemoryDatabaseBuilder(context, RatesDatabase.class).build();
        conversionRateDao = ratesDatabase.getConversionRateDao();
    }

    @After
    public void closeDb() {
        ratesDatabase.close();
    }

    @Test
    public void createRateAndGetByCode() {
        // Given
        ConversionRateTable rate = getSampleRate(1, "COP", 3000.0);

        // When
        conversionRateDao.insertAll(rate);

        // Then
        assertEquals(1, conversionRateDao.countAll());
        assertEquals("COP", conversionRateDao.getByCode("COP").getCode());
    }

    @Test
    public void createRateAndGetById() {
        // Given
        ConversionRateTable rate = getSampleRate(1, "COP", 3000.0);

        // When
        conversionRateDao.insertAll(rate);

        // Then
        assertEquals("COP", conversionRateDao.getById(1).getCode());
    }

    @Test
    public void deleteRateAndCount() {
        // Given
        ConversionRateTable rate = getSampleRate(1, "COP", 3000.0);

        // When
        conversionRateDao.insertAll(rate);
        conversionRateDao.delete(rate);

        // Then
        assertEquals(0, conversionRateDao.countAll());
    }

    @Test
    public void clearRateAndCount() {
        // Given
        ConversionRateTable rate1 = getSampleRate(1, "COP", 3000.0);
        ConversionRateTable rate2 = getSampleRate(2, "EUR", 1.5);
        ConversionRateTable rate3 = getSampleRate(3, "JPY", 2.2);

        // When
        conversionRateDao.insertAll(rate1);
        conversionRateDao.insertAll(rate2);
        conversionRateDao.insertAll(rate3);
        conversionRateDao.clear();

        // Then
        assertEquals(0, conversionRateDao.countAll());
    }

    @Test
    public void getAllEntries() {
        // Given
        ConversionRateTable rate1 = getSampleRate(1, "COP", 3000.0);
        ConversionRateTable rate2 = getSampleRate(2, "EUR", 1.5);
        ConversionRateTable rate3 = getSampleRate(3, "JPY", 2.2);

        // When
        conversionRateDao.insertAll(rate1);
        conversionRateDao.insertAll(rate2);
        conversionRateDao.insertAll(rate3);
        List<ConversionRateTable> rates = conversionRateDao.getAll();

        // Then
        assertEquals(3, conversionRateDao.countAll());
        assertEquals(3, rates.size());
    }

    private ConversionRateTable getSampleRate(int id, String code, Double rate) {
        ConversionRateTable conversionRateTable = new ConversionRateTable();
        conversionRateTable.setUid(id);
        conversionRateTable.setCode(code);
        conversionRateTable.setRate(rate);
        return conversionRateTable;
    }

}
