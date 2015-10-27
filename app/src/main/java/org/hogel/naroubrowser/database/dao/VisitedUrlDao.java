package org.hogel.naroubrowser.database.dao;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.hogel.naroubrowser.service.DatabaseService;

@Singleton
public class VisitedUrlDao {
    @Inject
    DatabaseService databaseService;

    @Inject
    public VisitedUrlDao() {
    }

    public void create(String url, String title) {
        databaseService.execute("INSERT INTO visited_urls (url, title) VALUES (?, ?);", url, title);
    }

    public boolean isExist(String url) {
        return databaseService.isExists("SELECT 1 FROM visited_urls WHERE url = ? LIMIT 1;", url);
    }
}
