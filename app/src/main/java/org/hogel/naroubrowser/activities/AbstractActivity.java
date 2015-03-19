package org.hogel.naroubrowser.activities;

import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
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

    protected BrowserComponent component() {
        return BrowserApplication.component(this);
    }
}
