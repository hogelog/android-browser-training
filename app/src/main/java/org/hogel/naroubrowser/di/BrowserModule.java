package org.hogel.naroubrowser.di;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.google.inject.Binder;
import com.google.inject.Provides;
import org.hogel.naroubrowser.R;

import javax.inject.Singleton;

public class BrowserModule implements com.google.inject.Module {

    @Override
    public void configure(Binder binder) {
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
    Resources provideResources(Context context) {
        return context.getResources();
    }

    @Provides
    @Singleton
    AssetManager provideAssetManager(Resources resources) {
        return resources.getAssets();
    }
}
