package com.urise.webapp.model;

import java.net.URL;
import java.time.YearMonth;

public class CompanyContent {
    URL homepage;
    YearMonth started;
    YearMonth finished;
    String title;
    String description;

    public CompanyContent(URL homepage, YearMonth started, YearMonth finished, String title, String description) {
        this.homepage = homepage;
        this.started = started;
        this.finished = finished;
        this.title = title;
        this.description = description;
    }
    public URL getHomepage() {
        return homepage;
    }

    public YearMonth getStarted() {
        return started;
    }

    public YearMonth getFinished() {
        return finished;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "CompanyContent{" +
                "homepage=" + homepage +
                ", started=" + started +
                ", finished=" + finished +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

}
