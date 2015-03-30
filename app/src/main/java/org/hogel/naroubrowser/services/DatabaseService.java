package org.hogel.naroubrowser.services;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import org.hogel.naroubrowser.db.BrowserDatabaseHelper;
import org.hogel.naroubrowser.db.FlywayHelper;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DatabaseService {
    @Inject
    BrowserDatabaseHelper databaseHelper;

    @Inject
    Context context;

    @Inject
    public DatabaseService() {
    }

    public void execute(String sql, String... values) {
        try (SQLiteDatabase database = databaseHelper.getWritableDatabase()) {
            database.execSQL(sql, values);
        }
    }

    public <T> T query(Class<T> klass, String sql, String... values) {
        try (SQLiteDatabase database = databaseHelper.getReadableDatabase()) {
            try (Cursor cursor = database.rawQuery(sql, values)) {
                cursor.moveToFirst();
                if (cursor.getCount() == 0) {
                    return null;
                }
                return klass.cast(getValue(cursor, 0));
            }
        }
    }

    public boolean isExists(String sql, String... values) {
        Object result = query(Object.class, sql, values);
        return result != null;
    }

    private Object getValue(Cursor cursor, int columnIndex) {
        switch (cursor.getType(columnIndex)) {
            case Cursor.FIELD_TYPE_NULL:
                return null;
            case Cursor.FIELD_TYPE_INTEGER:
                return cursor.getLong(columnIndex);
            case Cursor.FIELD_TYPE_FLOAT:
                return cursor.getDouble(columnIndex);
            case Cursor.FIELD_TYPE_STRING:
                return cursor.getString(columnIndex);
            case Cursor.FIELD_TYPE_BLOB:
                return cursor.getBlob(columnIndex);
        }
        throw new IllegalStateException("Unknown sqlite type: " + cursor.getType(columnIndex));
    }

    public void migrate() {
        try (SQLiteDatabase database = databaseHelper.getWritableDatabase()) {
            FlywayHelper.migrate(context, database);
        }
    }
}
