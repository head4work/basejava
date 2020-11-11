package com.urise.webapp.model;

import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static com.urise.webapp.model.ContactTypes.*;

class ResumeTest {
    Resume resume_1 = new Resume("name1");
    SectionData date = new SectionData(new Date(2000, Calendar.FEBRUARY, 1));
    SectionText text = new SectionText("text");

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

    @Test
    void addSection() {
        resume_1.addSection(SectionTypes.OBJECTIVE, new Section(text, date, null, null));

        System.out.println(resume_1.getSections());
    }

}