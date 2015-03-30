package org.hogel.naroubrowser.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.android.ContextHolder;

public class FlywayHelper {
    public static void migrate(Context context, SQLiteDatabase database) {
        ContextHolder.setContext(context.getApplicationContext());
        Flyway flyway = new Flyway();
        flyway.setDataSource("jdbc:sqlite:" + database.getPath(), "", "");
        flyway.migrate();
    }
}
