package org.hogel.naroubrowser.di;

import android.content.Context;
import com.crashlytics.android.answers.Answers;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.hogel.naroubrowser.R;

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
    Answers provideAnswers() {
        return Answers.getInstance();
    }
}
