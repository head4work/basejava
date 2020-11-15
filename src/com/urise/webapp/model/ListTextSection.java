package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;

public class ListTextSection extends Section<List<String>> {
   List<String> list;

    public ListTextSection(List<String> list) {
        super(list);
        this.list = list;
    }

    public List<String> getList() {
        return list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListTextSection that = (ListTextSection) o;
        return list.equals(that.list);
    }

    @Override
    public int hashCode() {
        return Objects.hash(list);
    }
}
