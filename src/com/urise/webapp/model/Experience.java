package com.urise.webapp.model;

import java.net.URL;
import java.time.YearMonth;
import java.util.Objects;

public class Experience {
    private final URL homepage;
    private final YearMonth started;
    private final YearMonth finished;
    private final String title;
    private final String description;

    public Experience(URL homepage, YearMonth started, YearMonth finished, String title, String description) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Experience that = (Experience) o;
        return Objects.equals(homepage, that.homepage) &&
                started.equals(that.started) &&
                Objects.equals(finished, that.finished) &&
                title.equals(that.title) &&
                description.equals(that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(homepage, started, finished, title, description);
    }

    @Override
    public String toString() {
        return "Company homepage: " + homepage +
                ", started: " + started +
                ", finished: " + finished +
                ", company name:'" + title +
                "', experience description: " + description;
    }

}
