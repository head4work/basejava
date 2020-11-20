package com.urise.webapp.model;

import java.net.URL;
import java.time.YearMonth;
import java.util.Objects;

public class Experience {
    private final String company;
    private final URL homepage;
    private final YearMonth started;
    private YearMonth started2;
    private final YearMonth finished;
    private YearMonth finished2;
    private final String title;
    private String title2;
    private String description;

    public Experience(String company, URL homepage, YearMonth started, YearMonth finished, String title, String description) {
        this.company = company;
        this.homepage = homepage;
        this.started = started;
        this.finished = finished;
        this.title = title;
        this.description = description;
    }

    public Experience(String company, URL homepage, YearMonth started, YearMonth started2,
                      YearMonth finished, YearMonth finished2, String title, String title2) {
        this.company = company;
        this.homepage = homepage;
        this.started = started;
        this.started2 = started2;
        this.finished = finished;
        this.finished2 = finished2;
        this.title = title;
        this.title2 = title2;
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

    public String getCompany() {
        return company;
    }

    public YearMonth getStarted2() {
        return started2;
    }

    public YearMonth getFinished2() {
        return finished2;
    }

    public String getTitle2() {
        return title2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Experience that = (Experience) o;
        return company.equals(that.company) &&
                Objects.equals(homepage, that.homepage) &&
                started.equals(that.started) &&
                Objects.equals(started2, that.started2) &&
                finished.equals(that.finished) &&
                Objects.equals(finished2, that.finished2) &&
                title.equals(that.title) &&
                Objects.equals(title2, that.title2) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(company, homepage, started, started2, finished, finished2, title, title2, description);
    }

    @Override
    public String toString() {
        if (started2 != null) {
            return company + "\n" +
                    homepage + "\n" +
                    started + " - " + finished + "   " + title + "\n" +
                    started2 + " - " + finished2 + "   " + title2 + "\n";
        }
        return company + "\n" +
                homepage + "\n" +
                started + " - " + finished + "   " + title + "\n" +
                description;
    }
}
