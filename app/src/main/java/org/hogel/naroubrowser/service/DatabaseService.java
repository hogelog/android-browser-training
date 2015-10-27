package org.hogel.naroubrowser.service;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.hogel.naroubrowser.database.BrowserDatabaseHelper;


@Singleton
public class DatabaseService {
    private final Context context;

    private final BrowserDatabaseHelper databaseHelper;

    @Inject
    public DatabaseService(Context context, BrowserDatabaseHelper databaseHelper) {
        this.context = context;
        this.databaseHelper = databaseHelper;
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
}
