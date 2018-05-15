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
        ConversionRate rate = getSampleRate(1, "COP", "Colombian peso", "USD");

        // When
        conversionRateDao.insertAll(rate);

        // Then
        assertEquals(1, conversionRateDao.countAll());
        assertEquals("Colombian peso", conversionRateDao.getByCode("COP").getName());
    }

    @Test
    public void createRateAndGetById() {
        // Given
        ConversionRate rate = getSampleRate(1, "COP", "Colombian peso", "USD");

        // When
        conversionRateDao.insertAll(rate);

        // Then
        assertEquals("Colombian peso", conversionRateDao.getById(1).getName());
    }

    @Test
    public void deleteRateAndCount() {
        // Given
        ConversionRate rate = getSampleRate(1, "COP", "Colombian peso", "USD");

        // When
        conversionRateDao.insertAll(rate);
        conversionRateDao.delete(rate);

        // Then
        assertEquals(0, conversionRateDao.countAll());
    }

    @Test
    public void clearRateAndCount() {
        // Given
        ConversionRate rate1 = getSampleRate(1, "COP", "Colombian peso", "USD");
        ConversionRate rate2 = getSampleRate(2, "EUR", "Euro", "USD");
        ConversionRate rate3 = getSampleRate(3, "JPY", "Japan Yen", "USD");

        // When
        conversionRateDao.insertAll(rate1);
        conversionRateDao.insertAll(rate2);
        conversionRateDao.insertAll(rate3);
        conversionRateDao.clear();

        // Then
        assertEquals(0, conversionRateDao.countAll());
    }

    @Test
    public void filterPerBase() {
        // Given
        ConversionRate rate1 = getSampleRate(1, "COP", "Colombian peso", "USD");
        ConversionRate rate2 = getSampleRate(2, "EUR", "Euro", "USD");
        ConversionRate rate3 = getSampleRate(3, "JPY", "Japan Yen", "USD");
        ConversionRate rate4 = getSampleRate(4, "CLP", "Chile peso", "EUR");

        // When
        conversionRateDao.insertAll(rate1);
        conversionRateDao.insertAll(rate2);
        conversionRateDao.insertAll(rate3);
        conversionRateDao.insertAll(rate4);
        List<ConversionRate> rates = conversionRateDao.getAllByBase("USD");

        // Then
        assertEquals(4, conversionRateDao.countAll());
        assertEquals(3, rates.size());
        assertEquals(3, conversionRateDao.countRatesByBase("USD"));
    }

    private ConversionRate getSampleRate(int id, String code, String name, String base) {
        ConversionRate conversionRate = new ConversionRate();
        conversionRate.setUid(id);
        conversionRate.setCode(code);
        conversionRate.setName(name);
        conversionRate.setBase(base);
        conversionRate.setFlag("https://restcountries.eu/data/col.svg");
        conversionRate.setRate(3000.0);
        return conversionRate;
    }

}
