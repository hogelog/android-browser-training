package org.hogel.naroubrowser.di;

import android.content.res.AssetManager;
import dagger.Component;
import org.hogel.naroubrowser.BrowserApplication;
import org.hogel.naroubrowser.activities.AboutActivity;
import org.hogel.naroubrowser.activities.MainActivity;
import org.hogel.naroubrowser.db.dao.VisitedUrlDao;
import org.hogel.naroubrowser.services.AnalyticsService;
import org.hogel.naroubrowser.services.DatabaseService;
import org.hogel.naroubrowser.views.MainWebView;

import javax.inject.Singleton;

@Singleton
@Component(modules = {BrowserModule.class})
public interface BrowserComponent {

    final class Initializer {
        public static BrowserComponent init(BrowserApplication application) {
            return DaggerBrowserComponent
                .builder()
                .browserModule(new BrowserModule(application))
                .build();
        }
    }

    void inject(MainActivity activity);

    void inject(AboutActivity activity);

    void inject(MainWebView mainWebView);

    AnalyticsService getAnalyticsService();

    DatabaseService getDatabaseService();

    VisitedUrlDao getVisitedUrlDao();

    AssetManager getAssetManager();
}
