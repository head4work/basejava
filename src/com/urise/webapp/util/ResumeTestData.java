package com.urise.webapp.util;

import com.urise.webapp.model.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.urise.webapp.model.ContactTypes.*;
import static com.urise.webapp.model.SectionTypes.*;

public class ResumeTestData {


    public static void main(String[] args) throws MalformedURLException {
        Resume resume_1 = new Resume("Григорий Кислин\n");

        System.out.println("\n" + resume_1.getFullName());
        resumeFillData(resume_1);

        printContacts(resume_1);
        printSections(resume_1);
    }

    public static void resumeFillData(Resume resume_1) throws MalformedURLException {
        resumeAddContacts(resume_1);
        resumeAddSections(resume_1);
    }

    private static void resumeAddSections(Resume resume_1) throws MalformedURLException {
        resume_1.addSection(OBJECTIVE,
                new SingleTextAbstractSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям\n"));
        resume_1.addSection(PERSONAL,
                new SingleTextAbstractSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.\n"));

        List<String> sectionListAchievement = new ArrayList<>();
        resume_1.addSection(ACHIEVEMENT, new BulletedListAbstractSection(sectionListAchievement));
        sectionListAchievement.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven." +
                " Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\"." +
                " Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.\n");
        sectionListAchievement.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike." +
                " Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.\n");

        List<String> sectionListQualification = new ArrayList<>();
        resume_1.addSection(QUALIFICATION, new BulletedListAbstractSection(sectionListQualification));
        sectionListQualification.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2\n");
        sectionListQualification.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce\n");

        List<Experience> sectionListExperience = new ArrayList<>();

        Experience javaOnlineProjects = new Experience("Java Online Projects", new URL("http://javaops.ru/"),
                YearMonth.of(2013, 10), YearMonth.now(), "Автор проекта.",
                "Создание, организация и проведение Java онлайн проектов и стажировок.\n");
        sectionListExperience.add(javaOnlineProjects);
        resume_1.addSection(EXPERIENCE, new Company(sectionListExperience));

        Experience coursera = new Experience("Coursera", new URL("https://www.coursera.org/course/progfun"),
                YearMonth.of(2013, 2), YearMonth.of(2013, 5), "\"Functional Programming Principles in Scala\" by Martin Odersky",
                "");
        sectionListExperience.add(coursera);

        List<Experience> sectionListEducation = new ArrayList<>();
        resume_1.addSection(EDUCATION, new Company(sectionListEducation));

        Experience sifmo = new Experience("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики",
                new URL("http://www.ifmo.ru/"),
                YearMonth.of(1993, 9), YearMonth.of(1996, 7), YearMonth.of(1987, 9), YearMonth.of(1993, 7),
                "Аспирантура (программист С, С++)", "Инженер (программист Fortran, C)");
        sectionListEducation.add(sifmo);
        resume_1.addSection(EDUCATION, new Company(sectionListEducation));
    }

    private static void resumeAddContacts(Resume resume_1) {
        resume_1.addContact(PHONE, "+7(921) 855-0482");
        resume_1.addContact(SKYPE, "grigory.kislin");
        resume_1.addContact(EMAIL, "gkislin@yandex.ru");
        resume_1.addContact(LINKEDIN, "https://www.linkedin.com/in/gkislin");
        resume_1.addContact(GITHUB, "https://github.com/gkislin");
        resume_1.addContact(STACKOVERFLOW, "https://stackoverflow.com/users/548473");
        resume_1.addContact(HOMEPAGE, "http://gkislin.ru/\n");
    }

    private static void printSections(Resume resume_1) {
        Map<SectionTypes, Object> map1 = resume_1.getSections();
        map1.forEach((k, v) -> System.out.println(k.getTitle() + "\n" + v.toString()));
    }

    private static void printContacts(Resume resume_1) {
        Map<ContactTypes, String> map = resume_1.getContacts();
        map.forEach((k, v) -> System.out.println(k.getContact() + "  -  " + v));
    }
}
