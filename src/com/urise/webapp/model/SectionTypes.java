package com.urise.webapp.model;

public enum SectionTypes {
    OBJECTIVE("Позиция"), PERSONAL("Личные качетва"), ACHIEVEMENT("Достижения"), QUALIFICATION("Квалификация"), EXPERIENCE("Опыт работы"), EDUCATION("Образование");

    private final String title;

    SectionTypes(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

}
