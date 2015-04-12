package org.hogel.naroubrowser.activities;

import android.content.Context;
import android.content.Intent;
import com.mikepenz.aboutlibraries.Libs;
import com.mikepenz.aboutlibraries.ui.LibsActivity;
import org.hogel.naroubrowser.R;

public class AboutActivity extends LibsActivity {
    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, AboutActivity.class);
        intent.putExtra(Libs.BUNDLE_FIELDS, Libs.toStringArray(R.string.class.getFields()));
        intent.putExtra(Libs.BUNDLE_VERSION, true);
        intent.putExtra(Libs.BUNDLE_LICENSE, true);
        intent.putExtra(Libs.BUNDLE_THEME, R.style.AppTheme);
        return intent;
    }
}
