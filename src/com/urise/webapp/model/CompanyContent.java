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


}
