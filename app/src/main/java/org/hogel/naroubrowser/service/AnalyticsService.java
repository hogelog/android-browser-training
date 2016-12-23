package org.hogel.naroubrowser.service;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class AnalyticsService {

    @Inject
    Tracker tracker;

    @Inject
    public AnalyticsService() {
    }

    public void trackViewUrl(String url) {
        tracker.send(
            new HitBuilders
                .EventBuilder()
                .setCategory("View")
                .setAction("Url")
                .setLabel(url)
                .build()
        );
    }

    public void trackMainMenu(String menu) {
        tracker.send(
            new HitBuilders
                .EventBuilder()
                .setCategory("MainMenu")
                .setAction(menu)
                .build()
        );
    }
}
