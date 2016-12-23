package org.hogel.naroubrowser.database.entity;

public class VisitedUrl {
    String url;

    String title;

    public VisitedUrl(String url, String title) {
        this.url = url;
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }
}
