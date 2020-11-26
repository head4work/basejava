package com.urise.webapp.model;

import java.net.URL;
import java.util.List;
import java.util.Objects;

public class Experience {
    private final String company;
    private final URL homepage;
    private List<Period> period;


    public Experience(String company, URL homepage, List<Period> period) {
        this.company = company;
        this.homepage = homepage;
        this.period = period;
    }

    public List<Period> getPeriod() {
        return period;
    }

    public URL getHomepage() {
        return homepage;
    }

    public String getCompany() {
        return company;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Experience that = (Experience) o;
        return company.equals(that.company) &&
                Objects.equals(homepage, that.homepage) &&
                period.equals(that.period);
    }

    @Override
    public int hashCode() {
        return Objects.hash(company, homepage, period);
    }

    @Override
    public String toString() {
        return company + "\n" +
                homepage + "\n" +
                period
                ;
    }
}
