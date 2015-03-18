package org.hogel.naroubrowser.utils;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import javax.inject.Inject;

public class AnalyticsUtils {
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
