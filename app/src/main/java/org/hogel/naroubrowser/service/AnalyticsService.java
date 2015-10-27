package org.hogel.naroubrowser.service;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.ContentViewEvent;
import com.crashlytics.android.answers.CustomEvent;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class AnalyticsService {

    @Inject
    Tracker tracker;

    @Inject
    Answers answers;

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
        String shortUrl = url.substring(0, url.length() < 100 ? url.length() : 100);
        answers.logContentView(new ContentViewEvent().putContentName("VisitPage").putContentId(shortUrl));
    }

    public void trackMainMenu(String menu) {
        tracker.send(
            new HitBuilders
                .EventBuilder()
                .setCategory("MainMenu")
                .setAction(menu)
                .build()
        );
        answers.logCustom(new CustomEvent("MainMenu").putCustomAttribute("Name", menu));
    }
}
