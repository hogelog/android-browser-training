package org.hogel.naroubrowser;

import android.content.Context;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

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
}
