package org.hogel.naroubrowser.di;

import android.app.Application;
import com.google.inject.Injector;
import com.google.inject.Module;
import roboguice.RoboGuice;
import roboguice.inject.ContextScopedRoboInjector;
import roboguice.inject.RoboInjector;

public class Guices {
    public static RoboInjector initialize(Application application) {
        final Injector injector = RoboGuice.getOrCreateBaseApplicationInjector(application, RoboGuice.DEFAULT_STAGE, modules(application));
        return new ContextScopedRoboInjector(application, injector);
    }

    private static Module[] modules(Application application) {
        return new Module[] {
            RoboGuice.newDefaultRoboModule(application),
            new BrowserModule(),
        };
    }
}
