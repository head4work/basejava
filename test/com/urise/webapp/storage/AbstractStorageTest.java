package com.urise.webapp.storage;

import com.urise.webapp.exeption.ExistStorageException;
import com.urise.webapp.exeption.NotExistStorageException;
import com.urise.webapp.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static com.urise.webapp.model.ContactTypes.*;
import static com.urise.webapp.model.SectionTypes.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

abstract class AbstractStorageTest {
    Storage storage;
    static final String UUID_1 = "uuid1";
    static final String UUID_2 = "uuid2";
    static final String UUID_3 = "uuid3";
    static final String UUID_dummy = "dummy";
    static final String UUID_777 = "uuid777";
    static Resume resume_1 = new Resume(UUID_1, "Григорий Кислин");
    static Resume resume_2 = new Resume(UUID_2, "Petr Zaicev");
    static Resume resume_3 = new Resume(UUID_3, "Mark Avreli");
    static Resume resume_777 = new Resume(UUID_777, "Juan Lopez");

    AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    void setUp() throws MalformedURLException {
        storage.clear();
        storage.save(resume_1);
        resume_1.addContact(PHONE, "+7(921) 855-0482");
        resume_1.addContact(SKYPE, "grigory.kislin");
        resume_1.addContact(EMAIL, "gkislin@yandex.ru");
        resume_1.addContact(LINKEDIN, "https://www.linkedin.com/in/gkislin");
        resume_1.addContact(GITHUB, "https://github.com/gkislin");
        resume_1.addContact(STACKOVERFLOW, "https://stackoverflow.com/users/548473");
        resume_1.addContact(HOMEPAGE, "http://gkislin.ru/\n");
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

        List<String> sectionListExperience = new ArrayList<>();
        resume_1.addSection(QUALIFICATION, new BulletedListAbstractSection(sectionListExperience));
        sectionListExperience.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2\n");
        sectionListExperience.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce\n");

        Experience javaOnlineProjects = new Experience("Java Online Projects", new URL("http://javaops.ru/"),
                YearMonth.of(2013, 10), YearMonth.now(), "Автор проекта.",
                "Создание, организация и проведение Java онлайн проектов и стажировок.\n");
        List<Experience> sectionListExperience1 = new ArrayList<>();
        sectionListExperience1.add(javaOnlineProjects);
        resume_1.addSection(EXPERIENCE, new Company(sectionListExperience1));

        Experience coursera = new Experience("Coursera", new URL("https://www.coursera.org/course/progfun"),
                YearMonth.of(2013, 2), YearMonth.of(2013, 5), "\"Functional Programming Principles in Scala\" by Martin Odersky",
                "");
        List<Experience> sectionListExperience2 = new ArrayList<>();
        sectionListExperience2.add(coursera);
        resume_1.addSection(EDUCATION, new Company(sectionListExperience2));

        Experience sifmo = new Experience("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики",
                new URL("http://www.ifmo.ru/"),
                YearMonth.of(1993, 9), YearMonth.of(1996, 7), YearMonth.of(1987, 9), YearMonth.of(1993, 7),
                "Аспирантура (программист С, С++)", "Инженер (программист Fortran, C)");
        List<Experience> sectionListExperience3 = new ArrayList<>();
        sectionListExperience3.add(sifmo);
        resume_1.addSection(EDUCATION, new Company(sectionListExperience3));
        storage.save(resume_2);
        storage.save(resume_3);
    }

    @Test
    void save() {
        int sizeBefore = storage.size();
        storage.save(resume_777);
        assertEquals(sizeBefore + 1, storage.size());
        assertEquals(resume_777, storage.get(UUID_777));
    }

    @Test
    void saveAlreadyExistUuid() {
        assertThrows(ExistStorageException.class, () -> storage.save(resume_1));
    }

    @Test
    void delete() {
        int sizeBefore = storage.size();
        storage.delete(UUID_1);
        assertEquals(sizeBefore - 1, storage.size());
        assertThrows(NotExistStorageException.class, () -> storage.get(UUID_1));
    }

    @Test
    void deleteNotExistUuid() {
        assertThrows(NotExistStorageException.class, () -> storage.delete(UUID_dummy));
    }

    @Test
    void get() {
        assertEquals(resume_1, storage.get(UUID_1));
        System.out.println(storage.get(UUID_1));
    }

    @Test
    void getNotExistUuid() {
        assertThrows(NotExistStorageException.class, () -> storage.get(UUID_dummy));
    }

    @Test
    void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    void getAllSorted() {
        List<Resume> expectedResumes = Arrays.asList(resume_1, resume_2, resume_3);
        expectedResumes.sort(Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid));
        assertEquals(expectedResumes, storage.getAllSorted());
    }

    @Test
    void update() {
        storage.update(resume_1);
        assertEquals(resume_1, storage.get(UUID_1));
    }

    @Test
    void updateNotExistUuid() {
        assertThrows(NotExistStorageException.class, () -> storage.update(new Resume(UUID_dummy, "dummy")));
    }

    @Test
    void size() {
        assertEquals(3, storage.size());
    }
}