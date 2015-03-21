package org.hogel.naroubrowser.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class BrowserDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "browser";
    private static final int DB_VERSION = 1;

    private static final String[] DB_CREATE_QUERIES = {
        "CREATE TABLE visited_urls (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "url TEXT, " +
            "title TEXT" +
            ")",
    };

    @Inject
    public BrowserDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for (String dbCreateQuery : DB_CREATE_QUERIES) {
            db.execSQL(dbCreateQuery);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
