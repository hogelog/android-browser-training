package org.hogel.naroubrowser.services;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AnalyticsService {
    public enum Category {
        VIEW,
        ;
    }

    public enum Action {
        URL,
        ;
    }

    @Inject
    GoogleAnalytics googleAnalytics;

    @Inject
    Tracker tracker;

    @Inject
    public AnalyticsService() {
    }

    public void trackViewUrl(String url) {
        tracker.send(
            new HitBuilders
                .EventBuilder()
                .setCategory(Category.VIEW.name())
                .setAction(Action.URL.name())
                .setLabel(url)
                .build()
        );
    }
}
