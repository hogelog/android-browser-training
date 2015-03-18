package org.hogel.naroubrowser;

import android.app.Application;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.splunk.mint.Mint;

public class BrowserApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Mint.initAndStartSession(this, BuildConfig.MINT_API_KEY);
        if (BuildConfig.DEBUG) {
            Mint.enableDebug();
            Mint.flush();
        }
    }

    public synchronized Tracker getTracker() {
        GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
        Tracker tracker = analytics.newTracker(R.xml.app_tracker);
        return tracker;
    }
}
