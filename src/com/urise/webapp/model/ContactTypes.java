package com.urise.webapp.model;

public enum ContactTypes {

    PHONE("Телефон"),
    SKYPE("Скайп"),
    EMAIL("Электронна почта"),
    LINKEDIN("Линкдин"),
    GITHUB("Гитхаб"),
    STACKOVERFLOW("Стаковерфлоу"),
    HOMEPAGE("Домашняястраница");

    private final String contact;

    ContactTypes(String contact) {
        this.contact = contact;
    }

    public String getContact() {
        return contact;
    }
}
