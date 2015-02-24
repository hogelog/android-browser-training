package org.hogel.browser.activities;

import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;

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
}
