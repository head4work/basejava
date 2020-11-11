package com.urise.webapp.model;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

/**
 * Initial resume class
 */
public class Resume {


    // Unique identifier
    private final String uuid;
    private final String fullName;
    private final HashMap<ContactTypes, String> contacts = new HashMap<>();
    private final HashMap<SectionTypes, String> sections = new HashMap<>();

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(fullName, "fullName must not be null");
        this.uuid = uuid;
        this.fullName = fullName;

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

    public HashMap<ContactTypes, String> getContacts() {
        return contacts;
    }

    public HashMap<SectionTypes, String> getSections() {
        return sections;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return fullName != null ? fullName.equals(resume.fullName) : resume.fullName == null;
    }

    @Override
    public int hashCode() {
        return fullName != null ? fullName.hashCode() : 0;
    }

    @Override
    public String toString() {
        return uuid;
    }

}
