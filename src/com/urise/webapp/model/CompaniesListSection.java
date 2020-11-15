package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;

public class CompaniesListSection extends Section<List<CompanyContent>> {
List<CompanyContent> list;

    public CompaniesListSection(List<CompanyContent> list) {
        super(list);
        this.list = list;
    }

    public List<CompanyContent> getList() {
        return list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompaniesListSection that = (CompaniesListSection) o;
        return list.equals(that.list);
    }

    @Override
    public int hashCode() {
        return Objects.hash(list);
    }

    @Override
    public String toString() {
        return "CompaniesListSection{" +
                "list=" + list +
                '}';
    }
}
