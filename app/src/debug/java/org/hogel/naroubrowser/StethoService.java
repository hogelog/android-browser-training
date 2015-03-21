package org.hogel.naroubrowser;

import com.facebook.stetho.Stetho;

public class StethoService {
    public static void initialize(BrowserApplication app) {
        Stetho.initialize(
            Stetho
                .newInitializerBuilder(app)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(app))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(app))
                .build()
        );
    }
}
