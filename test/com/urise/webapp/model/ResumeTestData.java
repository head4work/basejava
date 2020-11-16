package com.urise.webapp.model;

import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import static com.urise.webapp.model.ContactTypes.*;

class ResumeTestData {
    Resume resume_1 = new Resume("Григорий Кислин\n");
    URL homepage = new URL("http://javaops.ru/");
    YearMonth date = YearMonth.of(2013, 10);
    YearMonth date1 = YearMonth.now();
    Experience company = new Experience("Java Online Projects", homepage, date, date1, "Автор проекта.",
            "Создание, организация и проведение Java онлайн проектов и стажировок.\n");
    List<Experience> list = new ArrayList<>();
    List<String> blist = new ArrayList<>();

    ResumeTestData() throws MalformedURLException {
    }

    @Test
    void addContact() {
        System.out.println(resume_1.getFullName());
        resume_1.addContact(PHONE, "+7(921) 855-0482");
        resume_1.addContact(EMAIL, "gkislin@yandex.ru");
        resume_1.addContact(HOMEPAGE, "http://gkislin.ru/");

        System.out.println(resume_1.getContact(PHONE));
        System.out.println(resume_1.getContact(EMAIL));
        System.out.println(resume_1.getContact(HOMEPAGE));
    }


    @Test
    void addSection() {
        resume_1.addSection(SectionTypes.OBJECTIVE,
                new SingleTextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям\n"));

        resume_1.addSection(SectionTypes.ACHIEVEMENT, new BulletedListSection(blist));
        blist.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven." +
                " Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\"." +
                " Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.");
        blist.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike." +
                " Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        list.add(company);
        resume_1.addSection(SectionTypes.EXPERIENCE, new Company(list));

        System.out.println(SectionTypes.OBJECTIVE.getTitle() + "\n");
        System.out.println(resume_1.getSections().get(SectionTypes.OBJECTIVE));

        System.out.println(SectionTypes.ACHIEVEMENT.getTitle() + "\n");
        System.out.println(resume_1.getSections().get(SectionTypes.ACHIEVEMENT).toString() + "\n");

        System.out.println(SectionTypes.EXPERIENCE.getTitle() + "\n");
        System.out.println(resume_1.getSections().get(SectionTypes.EXPERIENCE).toString());

    }


}