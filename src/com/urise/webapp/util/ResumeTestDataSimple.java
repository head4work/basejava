package com.urise.webapp.util;

import com.urise.webapp.model.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static com.urise.webapp.model.ContactType.*;
import static com.urise.webapp.model.SectionType.*;

public class ResumeTestDataSimple {


    public static void main(String[] args) throws MalformedURLException {
        Resume resume_1 = createResumeWithSimpleData("uuid1", "Григорий Кислин\n");


        System.out.println("\n" + resume_1.getFullName());
        // printContacts(resume_1);
        printSections(resume_1);

    }

    public static Resume createResumeWithSimpleData(String uuid, String fullName) throws MalformedURLException {
        Resume resume_1 = new Resume(uuid, fullName);
        resume_1.addContact(PHONE, "+7(921) 855-0482");
        resume_1.addContact(SKYPE, "grigory.kislin");
        resume_1.addContact(EMAIL, "gkislin@yandex.ru");
        resume_1.addContact(LINKEDIN, "https://www.linkedin.com/in/gkislin");
        resume_1.addContact(GITHUB, "https://github.com/gkislin");
        resume_1.addContact(STACKOVERFLOW, "https://stackoverflow.com/users/548473");
        resume_1.addContact(HOMEPAGE, "http://gkislin.ru/\n");

        resume_1.addSection(OBJECTIVE,
                new TextSection("text"));
        resume_1.addSection(PERSONAL,
                new TextSection("text"));

        resume_1.addSection(ACHIEVEMENT, new ListSection("text",
                "text"));

        resume_1.addSection(QUALIFICATION, new ListSection("text",
                "text"));

        resume_1.addSection(EXPERIENCE, new OrganisationSection(new Organisation("Coursera",
                null, new Organisation.Position(YearMonth.of(2013, 2), YearMonth.of(2013, 5),
                "title", null))));

        resume_1.addSection(EDUCATION, new OrganisationSection(new Organisation("университет", new URL("https://www.spbgu.org")
                , new Organisation.Position(YearMonth.of(1993, 9), YearMonth.of(1996, 7), "Аспирантура ", "(программист С, С++)")
                , new Organisation.Position(YearMonth.of(1987, 9), YearMonth.of(1993, 7), "Инженер ", "(программист Fortran, C)"))));
        return resume_1;
    }


    private static void printSections(Resume resume_1) {
        Map<SectionType, Section> map1 = resume_1.getSections();
        map1.forEach(new BiConsumer<SectionType, Section>() {
            @Override
            public void accept(SectionType k, Section v) {
                System.out.println(k.name());
                switch (k) {
                    case OBJECTIVE, PERSONAL -> {
                        TextSection text = (TextSection) v;
                        System.out.println(text.getText());
                    }
                    case ACHIEVEMENT, QUALIFICATION -> {
                        List<String> list = ((ListSection) v).getList();
                        list.forEach(System.out::println);
                    }
                    case EDUCATION, EXPERIENCE -> {
                        List<Organisation> orgs = ((OrganisationSection) v).getOrganisationList();
                        orgs.forEach(new Consumer<Organisation>() {
                            @Override
                            public void accept(Organisation organisation) {
                                System.out.println(organisation.getCompany());
                                try {
                                    URL homepage = organisation.getHomepage() == null ? new URL("http://baeldung.com") : organisation.getHomepage();
                                    System.out.println(homepage);
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }
                                List<Organisation.Position> position = organisation.getPosition();
                                position.forEach(new Consumer<Organisation.Position>() {
                                    @Override
                                    public void accept(Organisation.Position position) {
                                        System.out.println(position.getStartDate());
                                        System.out.println(position.getFinishDate());
                                        System.out.println(position.getTitle());
                                        String description = (position.getDescription() == null) ? "" : position.getDescription();
                                        System.out.println(description);
                                    }
                                });

                            }
                        });
                    }
                }
                System.out.println();
            }
        });
    }

    private static void printSection(Resume resume_1) {
        Map<SectionType, Section> map1 = resume_1.getSections();
        map1.forEach(new BiConsumer<SectionType, Section>() {
            @Override
            public void accept(SectionType k, Section v) {
                System.out.println(k.name());
                switch (k) {
                    case OBJECTIVE, PERSONAL -> {
                        TextSection text = (TextSection) v;
                        System.out.println(text.getText());
                    }
                    case ACHIEVEMENT, QUALIFICATION -> {
                        List<String> list = ((ListSection) v).getList();
                        list.forEach(System.out::println);
                    }
                    case EDUCATION, EXPERIENCE -> {
                        List<Organisation> orgs = ((OrganisationSection) v).getOrganisationList();
                        orgs.forEach(new Consumer<Organisation>() {
                            @Override
                            public void accept(Organisation organisation) {
                                System.out.println(organisation.getCompany());
                                try {
                                    URL homepage = organisation.getHomepage() == null ? new URL("http://baeldung.com") : organisation.getHomepage();
                                    System.out.println(homepage);
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }
                                List<Organisation.Position> position = organisation.getPosition();
                                position.forEach(new Consumer<Organisation.Position>() {
                                    @Override
                                    public void accept(Organisation.Position position) {
                                        System.out.println(position.getStartDate());
                                        System.out.println(position.getFinishDate());
                                        System.out.println(position.getTitle());
                                        String description = (position.getDescription() == null) ? "" : position.getDescription();
                                        System.out.println(description);
                                    }
                                });

                            }
                        });
                    }
                }
                System.out.println();
            }
        });
    }


    private static void printContacts(Resume resume_1) {
        Map<ContactType, String> map = resume_1.getContacts();
        map.forEach((k, v) -> System.out.println(k.getTitle() + "  -  " + v));
    }
}

