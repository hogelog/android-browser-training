package org.hogel.naroubrowser.activities;

import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import roboguice.activity.RoboActionBarActivity;

public class AbstractActivity extends RoboActionBarActivity {

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
