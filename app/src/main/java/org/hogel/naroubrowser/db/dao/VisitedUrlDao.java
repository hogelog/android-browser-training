package org.hogel.naroubrowser.db.dao;

import org.hogel.naroubrowser.services.DatabaseService;

import javax.inject.Inject;
import javax.inject.Singleton;

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
}
