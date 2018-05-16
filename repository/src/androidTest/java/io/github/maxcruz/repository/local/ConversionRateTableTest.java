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

import io.github.maxcruz.repository.local.entity.ConversionRate;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class ConversionRateTest {

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
        ConversionRate rate = getSampleRate(1, "COP", 3000.0);

        // When
        conversionRateDao.insertAll(rate);

        // Then
        assertEquals(1, conversionRateDao.countAll());
        assertEquals("COP", conversionRateDao.getByCode("COP").getCode());
    }

    @Test
    public void createRateAndGetById() {
        // Given
        ConversionRate rate = getSampleRate(1, "COP", 3000.0);

        // When
        conversionRateDao.insertAll(rate);

        // Then
        assertEquals("COP", conversionRateDao.getById(1).getCode());
    }

    @Test
    public void deleteRateAndCount() {
        // Given
        ConversionRate rate = getSampleRate(1, "COP", 3000.0);

        // When
        conversionRateDao.insertAll(rate);
        conversionRateDao.delete(rate);

        // Then
        assertEquals(0, conversionRateDao.countAll());
    }

    @Test
    public void clearRateAndCount() {
        // Given
        ConversionRate rate1 = getSampleRate(1, "COP", 3000.0);
        ConversionRate rate2 = getSampleRate(2, "EUR", 1.5);
        ConversionRate rate3 = getSampleRate(3, "JPY", 2.2);

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
        ConversionRate rate1 = getSampleRate(1, "COP", 3000.0);
        ConversionRate rate2 = getSampleRate(2, "EUR", 1.5);
        ConversionRate rate3 = getSampleRate(3, "JPY", 2.2);

        // When
        conversionRateDao.insertAll(rate1);
        conversionRateDao.insertAll(rate2);
        conversionRateDao.insertAll(rate3);
        List<ConversionRate> rates = conversionRateDao.getAll();

        // Then
        assertEquals(3, conversionRateDao.countAll());
        assertEquals(3, rates.size());
    }

    private ConversionRate getSampleRate(int id, String code, Double rate) {
        ConversionRate conversionRate = new ConversionRate();
        conversionRate.setUid(id);
        conversionRate.setCode(code);
        conversionRate.setRate(rate);
        return conversionRate;
    }

}
