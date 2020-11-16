package com.urise.webapp.model;

import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import static com.urise.webapp.model.ContactTypes.*;

class ResumeTestData {
    Resume resume_1 = new Resume("name1");
    URL homepage = new URL("https://www.google.com/");
    YearMonth date = YearMonth.of(2000, 2);
    Experience company = new Experience(homepage, date, date, "Google", "some work in google");
    List<Experience> list = new ArrayList<>();

    ResumeTestData() throws MalformedURLException {
    }

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
        resume_1.addSection(SectionTypes.OBJECTIVE, new SingleTextSection("some text"));
        System.out.println(resume_1.getSections().get(SectionTypes.OBJECTIVE));
        System.out.println(resume_1.getSections().get(SectionTypes.OBJECTIVE).getClass());

        list.add(company);
        resume_1.addSection(SectionTypes.EXPERIENCE, new Company(list));
        //   System.out.println(resume_1.getSections().get(SectionTypes.EXPERIENCE).getContent().toString());
        System.out.println(resume_1.getSections().get(SectionTypes.EXPERIENCE).getClass());
        System.out.println(resume_1.getSections().get(SectionTypes.EXPERIENCE));

    }

}