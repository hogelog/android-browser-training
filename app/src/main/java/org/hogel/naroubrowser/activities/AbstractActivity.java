package org.hogel.naroubrowser.activities;

import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import com.github.hotchpotch.iconicfontengine.IconicFontEngine;
import org.hogel.naroubrowser.BrowserApplication;
import org.hogel.naroubrowser.di.BrowserComponent;

public class AbstractActivity extends ActionBarActivity {

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.inject(this);
    }

    @Override
    public void setContentView(View view) {
        throw new IllegalStateException("Unimplemented: call setContentView(int layoutResID)");
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        throw new IllegalStateException("Unimplemented: call setContentView(int layoutResID)");
    }

    public boolean inflateMenu(int menu_id, Menu menu) {
        getMenuInflater().inflate(menu_id, menu);
        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            if (!item.isVisible()) {
                continue;
            }
            item.set
            item.setTitle(IconicFontEngine.apply(item.getTitle()));
        }
        return true;
    }

    protected BrowserComponent component() {
        return BrowserApplication.component(this);
    }
}
