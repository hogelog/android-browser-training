package org.hogel.naroubrowser.activity;

import android.app.Activity;
import butterknife.ButterKnife;

public abstract class AbstractActivity extends Activity {

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
    }
}
