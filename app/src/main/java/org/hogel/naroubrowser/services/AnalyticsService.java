package org.hogel.naroubrowser.services;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.inject.Inject;
import com.google.inject.Singleton;

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
