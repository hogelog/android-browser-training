package org.hogel.naroubrowser.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.webkit.WebView;
import butterknife.InjectView;
import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import org.hogel.naroubrowser.R;

import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class AboutActivity extends AbstractActivity {
    @Inject
    AssetManager assetManager;

    @InjectView(R.id.about_view)
    WebView webView;

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, AboutActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_about);

        setup();
    }

    private void setup() {
        try (InputStream htmlInput = assetManager.open("licenses.html")) {
            String html = CharStreams.toString(new InputStreamReader(htmlInput, Charsets.UTF_8));
            webView.loadData(html, "text/html", Charsets.UTF_8.name());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
