package com.urise.webapp.model;

public class SectionText {
    String value;

    @Override
    public String toString() {
        return "SectionText{" +
                "value='" + value + '\'' +
                '}';
    }

    public SectionText(String value) {
        this.value = value;
    }
}
