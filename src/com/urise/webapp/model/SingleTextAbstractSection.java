package com.urise.webapp.model;

import java.util.Objects;

public class SingleTextAbstractSection extends AbstractSection<String> {
    private final String text;

    public SingleTextAbstractSection(String text) {
        super(text);
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SingleTextAbstractSection that = (SingleTextAbstractSection) o;
        return text.equals(that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text);
    }

    @Override
    public String toString() {
        return text;
    }
}
