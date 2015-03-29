package org.hogel.naroubrowser.activities;

import android.app.ActionBar;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import butterknife.InjectView;
import org.hogel.naroubrowser.R;
import org.hogel.naroubrowser.consts.UrlConst;
import org.hogel.naroubrowser.db.dao.VisitedUrlDao;
import org.hogel.naroubrowser.services.AnalyticsService;
import org.hogel.naroubrowser.views.MainWebView;
import rx.functions.Action1;

import javax.inject.Inject;


public class MainActivity extends AbstractActivity {

    @Inject
    AnalyticsService analyticsService;

    @Inject
    VisitedUrlDao visitedUrlDao;

    @InjectView(R.id.main_webview)
    MainWebView mainWebview;

    @InjectView(R.id.progress_bar)
    ProgressBar progressBar;

    @InjectView(R.id.swipe_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    private ActionBar actionBar;

    private int actionBarScrollThreshold;

    private int scrolling = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        component().inject(this);

        setContentView(R.layout.activity_main);

        setup();

        if (savedInstanceState == null) {
            mainWebview.loadUrl(UrlConst.URL_LAUNCH);
        } else {
            mainWebview.restoreState(savedInstanceState);
        }
    }

    private void setup() {
        Resources resources = getResources();
        actionBarScrollThreshold = resources.getDimensionPixelSize(R.dimen.action_bar_scroll_threshold);

        mainWebview.listenScrollY(new Action1<Integer>() {
            @Override
            public void call(Integer y) {
                if (scrolling > 0 && y > 0) {
                    scrolling += y;
                } else if (scrolling < 0 && y < 0) {
                    scrolling += y;
                } else {
                    scrolling = y;
                }
                if (scrolling >= actionBarScrollThreshold) {
                    actionBar.hide();
                } else if (scrolling < -actionBarScrollThreshold) {
                    actionBar.show();
                }
            }
        }).listenProgress(new Action1<Integer>() {
            @Override
            public void call(Integer progress) {
                progressBar.setProgress(progress);
                if (progress == 0) {
                    progressBar.setVisibility(View.VISIBLE);
                } else if (progress == 100) {
                    progressBar.setVisibility(View.GONE);
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        }).listenVisitPage(new Action1<Pair<String, String>>() {
            @Override
            public void call(Pair<String, String> visitPage) {
                String url = visitPage.first;
                String title = visitPage.second;
                if (!visitedUrlDao.isExist(url)) {
                    visitedUrlDao.create(url, title);
                }
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mainWebview.reload();
            }
        });

        actionBar = getActionBar();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mainWebview.saveState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_bookmark) {
            mainWebview.loadUrl(UrlConst.URL_LAUNCH);
            return true;
        } else if (id == R.id.action_reload) {
            mainWebview.reload();
            return true;
        } else if (id == R.id.action_ranking) {
            mainWebview.loadUrl(UrlConst.URL_RANKING);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mainWebview.canGoBack()) {
            mainWebview.goBack();
            return;
        }
        super.onBackPressed();
    }
}
