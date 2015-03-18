package org.hogel.naroubrowser.di;

import android.app.Application;
import android.content.Context;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import dagger.Module;
import dagger.Provides;
import org.hogel.naroubrowser.BrowserApplication;
import org.hogel.naroubrowser.R;
import org.hogel.naroubrowser.utils.AnalyticsUtils;

import javax.inject.Singleton;

@Module
public class BrowserModule {

    private final Application application;

    public BrowserModule(Application application) {
        this.application = application;
    }

    @Provides
    Context provideContext(){
        return application;
    }

    @Provides
    @Singleton
    GoogleAnalytics provideGoogleAnalytics() {
        return GoogleAnalytics.getInstance(application);
    }

    @Provides
    @Singleton
    Tracker provideTracker(GoogleAnalytics googleAnalytics) {
        return googleAnalytics.newTracker(R.xml.app_tracker);
    }

    @Provides
    @Singleton
    AnalyticsUtils provideAnalyticsUtils() {
        AnalyticsUtils analyticsUtils = new AnalyticsUtils();
        component().inject(analyticsUtils);
        return analyticsUtils;
    }

    private BrowserComponent component() {
        return BrowserApplication.component(application);
    }
}