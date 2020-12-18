package com.urise.webapp.model;

import java.io.Serial;
import java.util.Objects;


public class TextSection extends Section {
    @Serial
    private static final long serialVersionUID = 1L;

    private String text;

    public TextSection() {
    }

    public TextSection(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextSection that = (TextSection) o;
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
