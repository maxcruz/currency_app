package io.github.maxcruz.repository.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.VisibleForTesting;

import io.github.maxcruz.repository.local.entity.ConversionRateTable;

/**
 * Data base to store currency rates
 */
@Database(entities = {ConversionRateTable.class}, version = 1)
public abstract class RatesDatabase extends RoomDatabase {

    public abstract ConversionRateDao getConversionRateDao();

    private static RatesDatabase INSTANCE;

    @VisibleForTesting
    private static final String DATABASE_NAME = "rates-database";

    /**
     * Return a single instance of the database
     *
     * @param context No matter which kind of Context is provided, to avoid memory leaks
     *                the Application Context is used.
     * @return Database instance to access or save currency rates.
     */
    public static RatesDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (RatesDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RatesDatabase.class, DATABASE_NAME)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
