package com.urise.webapp.model;

public enum ContactType {

    PHONE("Телефон"),
    SKYPE("Скайп"),
    EMAIL("Электронна почта"),
    LINKEDIN("Линкдин"),
    GITHUB("Гитхаб"),
    STACKOVERFLOW("Стаковерфлоу"),
    HOMEPAGE("Домашняястраница");

    private final String contact;

    ContactType(String contact) {
        this.contact = contact;
    }

    public String getContact() {
        return contact;
    }
}
