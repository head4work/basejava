package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;

public class Company extends Section<List<Experience>> {
    private final List<Experience> experienceList;

    public Company(List<Experience> experienceList) {
        super(experienceList);
        this.experienceList = experienceList;
    }

    public List<Experience> getExpirienceList() {
        return experienceList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company that = (Company) o;
        return experienceList.equals(that.experienceList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(experienceList);
    }

    @Override
    public String toString() {
        return "CompaniesListSection{" +
                "list=" + experienceList +
                '}';
    }
}
