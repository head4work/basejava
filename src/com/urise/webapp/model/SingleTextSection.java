package com.urise.webapp.model;

import java.util.Objects;

public class SingleTextSection extends Section<String> {
    private final String text;

    public SingleTextSection(String text) {
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
        SingleTextSection that = (SingleTextSection) o;
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
