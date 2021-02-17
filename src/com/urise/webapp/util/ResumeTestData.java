package com.urise.webapp.util;

import com.urise.webapp.model.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.urise.webapp.model.ContactType.*;
import static com.urise.webapp.model.SectionType.*;

public class ResumeTestData {


    public static void main(String[] args) throws MalformedURLException {
        Resume resume_1 = createResumeWithData("uuid1", "Григорий Кислин\n");


        System.out.println("\n" + resume_1.getFullName());
        printContacts(resume_1);
        printSections(resume_1);

    }

    public static Resume createResumeWithData(String uuid, String fullName) throws MalformedURLException {
        Resume resume_1 = new Resume(uuid, fullName);

        resume_1.addContact(PHONE, "+7(921) 855-0482");
        resume_1.addContact(SKYPE, "grigory.kislin");
        resume_1.addContact(EMAIL, "gkislin@yandex.ru");
        resume_1.addContact(LINKEDIN, "https://www.linkedin.com/in/gkislin");
        resume_1.addContact(GITHUB, "https://github.com/gkislin");
        resume_1.addContact(STACKOVERFLOW, "https://stackoverflow.com/users/548473");
        resume_1.addContact(HOMEPAGE, "http://gkislin.ru/");

        resume_1.addSection(OBJECTIVE,
                new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));
        resume_1.addSection(PERSONAL,
                new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));

        resume_1.addSection(ACHIEVEMENT, new ListSection("С 2013 года: разработка проектов" +
                " Разработка Web приложения,Java Enterprise, Многомодульный maven." +
                " Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие" +
                " (JMS/AKKA)." +
                " Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.",
                "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike." +
                        " Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk."));

        resume_1.addSection(QUALIFICATION, new ListSection("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2",
                "Version control: Subversion, Git, Mercury, ClearCase, Perforce"));


        List<Organisation> sectionListOrganisation = new ArrayList<>();
        List<Organisation.Position> javaOnlinePositions = new ArrayList<>();
        javaOnlinePositions.add(new Organisation.Position(YearMonth.of(2013, 10), YearMonth.now(), "Автор проекта.",
                "Создание, организация и проведение Java онлайн проектов и стажировок."));

        Organisation javaOnlineProjects = new Organisation("Java Online Projects", new URL("http://javaops.ru/"), javaOnlinePositions);


        sectionListOrganisation.add(javaOnlineProjects);
        resume_1.addSection(EXPERIENCE, new OrganisationSection(sectionListOrganisation));

        List<Organisation.Position> courseraPositions = new ArrayList<>();
        courseraPositions.add(new Organisation.Position(YearMonth.of(2013, 2), YearMonth.of(2013, 5),
                "Functional Programming Principles in Scala by Martin Odersky", ""));
        Organisation coursera = new Organisation("Coursera", new URL("https://www.coursera.org/course/progfun"), courseraPositions);
        sectionListOrganisation.add(coursera);

        List<Organisation> sectionListEducation = new ArrayList<>();
        resume_1.addSection(EDUCATION, new OrganisationSection(sectionListEducation));

        List<Organisation.Position> simfoPositions = new ArrayList<>();
        simfoPositions.add(new Organisation.Position(YearMonth.of(1993, 9), YearMonth.of(1996, 7), "Аспирантура ", "(программист С, С++)"));
        simfoPositions.add(new Organisation.Position(YearMonth.of(1987, 9), YearMonth.of(1993, 7), "Инженер ", "(программист Fortran, C)"));
        Organisation sifmo = new Organisation("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики",
                new URL("http://www.ifmo.ru/"), simfoPositions);
        sectionListEducation.add(sifmo);
        resume_1.addSection(EDUCATION, new OrganisationSection(sectionListEducation));
        return resume_1;
    }


    private static void printSections(Resume resume_1) {
        Map<SectionType, Section> map1 = resume_1.getSections();
        map1.forEach((k, v) -> System.out.println(k.getTitle() + "\n" + v.toString()));
    }

    private static void printContacts(Resume resume_1) {
        Map<ContactType, String> map = resume_1.getContacts();
        map.forEach((k, v) -> System.out.println(k.getTitle() + "  -  " + v));
    }
}

