package com.urise.webapp.model;

import org.junit.jupiter.api.Test;

import static com.urise.webapp.model.ContactTypes.*;

class ResumeTest {
    Resume resume_1 = new Resume("name1");

    @Test
    void addContact() {
        resume_1.addContact(PHONE, "911");
        resume_1.addContact(EMAIL, "email");
        resume_1.addContact(HOMEPAGE, "homepage");

        System.out.println(resume_1.getContact(PHONE));
        System.out.println(resume_1.getContact(EMAIL));
        System.out.println(resume_1.getContact(HOMEPAGE));
    }

    @Test
    void addContct() {
        resume_1.getContact(PHONE);
    }

}