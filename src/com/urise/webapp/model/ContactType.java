package com.urise.webapp.model;

public enum ContactType {

    PHONE("Телефон"),
    SKYPE("Скайп") {
        @Override
        public String toHtml0(String value) {
            return "<a href='skype:" + value + "'>" + value + "</a>";
        }
    },
    EMAIL("Электронна почта") {
        @Override
        public String toHtml0(String value) {
            return "<a href='mailto:" + value + "'>" + value + "</a>";
        }
    },
    LINKEDIN("Линкдин"),
    GITHUB("Гитхаб"),
    STACKOVERFLOW("Стаковерфлоу"),
    HOMEPAGE("Домашняястраница");

    private final String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    protected String toHtml0(String value) {
        return title + ": " + value;
    }

    public String toHtml(String value) {
        return (value == null) ? "" : toHtml0(value);
    }
}
