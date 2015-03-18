package org.hogel.naroubrowser.di;

import dagger.Component;
import org.hogel.naroubrowser.BrowserApplication;
import org.hogel.naroubrowser.activities.MainActivity;
import org.hogel.naroubrowser.utils.AnalyticsUtils;
import org.hogel.naroubrowser.views.MainWebView;

import javax.inject.Singleton;

@Singleton
@Component(modules = {BrowserModule.class})
public interface BrowserComponent {

    public static final class Initializer {
        public static BrowserComponent init(BrowserApplication application) {
            return Dagger_BrowserComponent
                .builder()
                .browserModule(new BrowserModule(application))
                .build();
        }
    }

    void inject(MainActivity activity);

    void inject(AnalyticsUtils utils);

    void inject(MainWebView mainWebView);
}
