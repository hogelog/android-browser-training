package org.hogel.naroubrowser.services;

import android.database.sqlite.SQLiteDatabase;
import org.hogel.naroubrowser.db.BrowserDatabaseHelper;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DatabaseService {
    @Inject
    BrowserDatabaseHelper databaseHelper;

    @Inject
    public DatabaseService() {
    }

    public void execute(String sql, String... values) {
        try (SQLiteDatabase database = databaseHelper.getWritableDatabase()) {
            database.execSQL(sql, values);
        }
    }
}
