package org.hogel.naroubrowser.db;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.facebook.stetho.common.Utf8Charset;
import com.google.common.io.CharStreams;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;

@Singleton
public class BrowserDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "application.db";
    private static final int DB_VERSION = 1;
    private static final String MIGRATION_DIR = "db/migration";

    @Inject
    AssetManager assetManager;

    @Inject
    public BrowserDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        migrate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        migrate(db);
    }

    private void migrate(SQLiteDatabase db) {
        try {
            String[] sqlPaths = assetManager.list(MIGRATION_DIR);
            Arrays.sort(sqlPaths);
            for (String sqlPath : sqlPaths) {
                executeSql(db, MIGRATION_DIR + "/" + sqlPath);
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private void executeSql(SQLiteDatabase db, String path) throws IOException {
        try (Reader reader = new InputStreamReader(assetManager.open(path, AssetManager.ACCESS_BUFFER), Utf8Charset.INSTANCE)) {
            String sql = CharStreams.toString(reader);
            db.execSQL(sql);
        }
    }
}
