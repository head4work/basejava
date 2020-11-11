package com.urise.webapp.model;

public class Section {
    private SectionText text;
    private SectionData date;
    private SectionList list;
    private SectionUrl url;

    @Override
    public String toString() {
        return String.format("Section text %s date %s list %s url %s", text, date, list, url);
    }

    public Section(SectionText text, SectionData date, SectionList list, SectionUrl url) {
        this.text = text;
        this.date = date;
        this.list = list;
        this.url = url;
    }
}
