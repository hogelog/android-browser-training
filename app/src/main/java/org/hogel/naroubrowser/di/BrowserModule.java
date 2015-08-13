package org.hogel.naroubrowser.di;

import android.content.Context;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.hogel.naroubrowser.R;
import org.hogel.naroubrowser.db.BrowserDatabaseHelper;
import org.hogel.naroubrowser.services.DatabaseService;

public class BrowserModule extends AbstractModule {
    @Override
    protected void configure() {
    }

    @Provides
    @Singleton
    GoogleAnalytics provideGoogleAnalytics(Context context) {
        return GoogleAnalytics.getInstance(context.getApplicationContext());
    }

    @Provides
    @Singleton
    Tracker provideTracker(GoogleAnalytics googleAnalytics) {
        return googleAnalytics.newTracker(R.xml.app_tracker);
    }

    @Provides
    @Singleton
    DatabaseService provideDatabaseService(Context context, BrowserDatabaseHelper databaseHelper) {
        DatabaseService databaseService = new DatabaseService(context, databaseHelper);
        databaseService.migrate();
        return databaseService;
    }
}
