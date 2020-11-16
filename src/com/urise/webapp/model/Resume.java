package com.urise.webapp.model;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Initial resume class
 */
public class Resume {


    // Unique identifier
    private final String uuid;
    private final String fullName;
    private final Map<ContactTypes, String> contacts = new EnumMap<>(ContactTypes.class);
    private final Map<SectionTypes, Section> sections = new EnumMap<>(SectionTypes.class);

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(fullName, "fullName must not be null");
        this.uuid = uuid;
        this.fullName = fullName;

    }

    void addSection(SectionTypes type, Section section) {
        sections.put(type, section);
    }

    void addContact(ContactTypes type, String value) {
        contacts.put(type, value);
    }

    String getContact(ContactTypes type) {
        return contacts.get(type);
    }

    void removeContact(ContactTypes type) {
        contacts.remove(type);
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public Map<ContactTypes, String> getContacts() {
        return contacts;
    }

    public Map<SectionTypes, Section> getSections() {
        return sections;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return uuid.equals(resume.uuid) &&
                fullName.equals(resume.fullName) &&
                Objects.equals(contacts, resume.contacts) &&
                Objects.equals(sections, resume.sections);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName, contacts, sections);
    }

    @Override
    public String toString() {
        return uuid;
    }

}
