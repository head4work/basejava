package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;

public class BulletedListAbstractSection extends AbstractSection<List<String>> {
    private final List<String> list;

    public BulletedListAbstractSection(List<String> list) {
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
        BulletedListAbstractSection that = (BulletedListAbstractSection) o;
        return list.equals(that.list);
    }

    @Override
    public int hashCode() {
        return Objects.hash(list);
    }

    @Override
    public String toString() {
        return (list + "").replace("[", "").replace("]", "").replace(",", "");
    }
}
