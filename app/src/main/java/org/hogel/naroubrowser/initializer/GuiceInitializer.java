package org.hogel.naroubrowser.initializer;

import android.app.Application;
import com.google.inject.Injector;
import com.google.inject.Module;
import org.hogel.naroubrowser.BrowserModule;
import roboguice.RoboGuice;
import roboguice.inject.ContextScopedRoboInjector;
import roboguice.inject.RoboInjector;

public class GuiceInitializer {
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
