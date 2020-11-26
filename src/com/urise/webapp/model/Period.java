package com.urise.webapp.model;

import java.time.YearMonth;

public class Period {
    private final YearMonth started;
    private final YearMonth finished;
    private final String title;
    private final String description;

    public Period(YearMonth started, YearMonth finished, String title, String description) {
        this.started = started;
        this.finished = finished;
        this.title = title;
        this.description = description;
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
        return started + " - " + finished + " " + title + " " +
                description + "\n"
                ;
    }
}
