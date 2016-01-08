package org.hogel.naroubrowser.activity;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.text.TextUtils;
import com.google.inject.Inject;
import org.hogel.naroubrowser.R;
import org.hogel.naroubrowser.constant.UrlConstant;

public class LaunchBrowserActivity extends AbstractActivity {

    @Inject
    Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CustomTabsIntent tabsIntent = new CustomTabsIntent.Builder()
            .setShowTitle(true)
            .setActionButton(decodeIcon(R.drawable.ic_folder_24dp), getString(R.string.action_bookmark), loadUrlIntent(UrlConstant.URL_LAUNCH))
            .addMenuItem(getString(R.string.action_ranking), loadUrlIntent(UrlConstant.URL_RANKING))
            .build();

        String url = getIntent().getDataString();
        if (TextUtils.isEmpty(url)) {
            tabsIntent.launchUrl(this, Uri.parse(UrlConstant.URL_LAUNCH));
        } else {
            tabsIntent.launchUrl(this, Uri.parse(url));
        }
        finish();
    }

    private Bitmap decodeIcon(int iconId) {
        return BitmapFactory.decodeResource(resources, iconId);
    }

    private PendingIntent loadUrlIntent(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url), this, LaunchBrowserActivity.class);
        return PendingIntent.getActivity(this, 0, intent, 0);
    }
}
