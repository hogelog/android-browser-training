package org.hogel.naroubrowser.activity;

import android.app.Activity;
import android.os.Bundle;
import com.google.inject.Key;
import roboguice.RoboGuice;
import roboguice.inject.RoboInjector;
import roboguice.util.RoboContext;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractActivity extends Activity implements RoboContext {
    protected HashMap<Key<?>,Object> scopedObjects = new HashMap<>();

    @Override
    public Map<Key<?>, Object> getScopedObjectMap() {
        return scopedObjects;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final RoboInjector injector = RoboGuice.getInjector(this);
        injector.injectMembersWithoutViews(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        RoboGuice.getInjector(this).injectViewMembers(this);
    }

    @Override
    protected void onDestroy() {
        try {
            RoboGuice.destroyInjector(this);
        } finally {
            super.onDestroy();
        }
    }
}
