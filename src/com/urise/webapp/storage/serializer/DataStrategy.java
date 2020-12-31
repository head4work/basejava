package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;

import java.io.*;
import java.net.URL;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class DataStrategy implements SerializeStrategy {

    private <K, V> void writeWithExceptionMap(Map<K, V> map, MyBiConsumer<K, V> action) throws IOException {
        Objects.requireNonNull(action);
        for (Map.Entry<K, V> entry : map.entrySet()) {
            K k = entry.getKey();
            V v = entry.getValue();
            action.accept(k, v);
        }
    }

    private <T> void writeWithException(Collection<T> collection, MyConsumerWriter<T> action) throws IOException {
        Objects.requireNonNull(action);
        for (T element : collection) {
            action.accept(element);
        }
    }

    @Override
    public void writeResume(Resume resume, OutputStream outputStream) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(outputStream)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());

            Map<ContactType, String> contacts = resume.getContacts();
            dos.writeInt(contacts.size());

            writeWithException(contacts.entrySet(), contactTypeStringEntry -> {
                dos.writeUTF(contactTypeStringEntry.getKey().name());
                dos.writeUTF(contactTypeStringEntry.getValue());
            });

                   /* writeWithExceptionMap(contacts, new MyBiConsumer<ContactType, String>() {
                        @Override
                        public void accept(ContactType contactType, String s) throws IOException {
                            dos.writeUTF((contactType.name()));
                            dos.writeUTF(s);
                        }
                    });*/

            Map<SectionType, Section> sections = resume.getSections();
            dos.writeInt(sections.size());
            writeWithException(sections.entrySet(), new MyConsumerWriter<Map.Entry<SectionType, Section>>() {
                @Override
                public void accept(Map.Entry<SectionType, Section> sectionTypeSectionEntry) throws IOException {
                    dos.writeUTF(sectionTypeSectionEntry.getKey().name());
                    switch (SectionType.valueOf(sectionTypeSectionEntry.getKey().name())) {
                        case OBJECTIVE, PERSONAL -> {
                            TextSection text = (TextSection) sectionTypeSectionEntry.getValue();
                            dos.writeUTF(text.getText());
                        }
                        case ACHIEVEMENT, QUALIFICATION -> {
                            ListSection list = (ListSection) sectionTypeSectionEntry.getValue();
                            dos.writeInt(list.getList().size());
                            for (String s : list.getList()) {
                                dos.writeUTF(s);
                            }
                        }
                        case EXPERIENCE, EDUCATION -> {
                            OrganisationSection organisations = (OrganisationSection) sectionTypeSectionEntry.getValue();
                            dos.writeInt(organisations.getOrganisationList().size());
                            for (Organisation o : organisations.getOrganisationList()) {
                                dos.writeUTF(o.getCompany());
                                dos.writeUTF(String.valueOf(o.getHomepage()));
                                dos.writeInt(o.getPosition().size());
                                for (Organisation.Position p : o.getPosition()) {
                                    dos.writeUTF(p.getStartDate().format(DateTimeFormatter.ofPattern("uuuu-MM")));
                                    dos.writeUTF(p.getFinishDate().format(DateTimeFormatter.ofPattern("uuuu-MM")));
                                    dos.writeUTF(p.getTitle());
                                    if (p.getDescription() == null) {
                                        p.setDescription("");
                                    }
                                    dos.writeUTF(p.getDescription());
                                }
                            }
                        }
                    }
                }
            });

           /* writeWithExceptionMap(sections, new MyBiConsumer<SectionType, Section>() {
                @Override
                public void accept(SectionType sectionType, Section section) throws IOException {
                    dos.writeUTF(sectionType.name());
                    switch (SectionType.valueOf(sectionType.name())) {
                        case OBJECTIVE, PERSONAL -> {
                            TextSection text = (TextSection) section;
                            dos.writeUTF(text.getText());
                        }
                        case ACHIEVEMENT, QUALIFICATION -> {
                            ListSection list = (ListSection) section;
                            dos.writeInt(list.getList().size());
                            for (String s : list.getList()) {
                                dos.writeUTF(s);
                            }
                        }
                        case EXPERIENCE, EDUCATION -> {
                            OrganisationSection organisations = (OrganisationSection) section;
                            dos.writeInt(organisations.getOrganisationList().size());
                            for (Organisation o : organisations.getOrganisationList()) {
                                dos.writeUTF(o.getCompany());
                                dos.writeUTF(String.valueOf(o.getHomepage()));
                                dos.writeInt(o.getPosition().size());
                                for (Organisation.Position p : o.getPosition()) {
                                    dos.writeUTF(p.getStartDate().format(DateTimeFormatter.ofPattern("uuuu-MM")));
                                    dos.writeUTF(p.getFinishDate().format(DateTimeFormatter.ofPattern("uuuu-MM")));
                                    dos.writeUTF(p.getTitle());
                                    if (p.getDescription() == null) {
                                        p.setDescription("");
                                    }
                                    dos.writeUTF(p.getDescription());
                                }
                            }
                        }
                    }
                }
            });*/
        }
    }

    @Override
    public Resume readResume(InputStream inputStream) throws IOException {
        try (DataInputStream dis = new DataInputStream(inputStream)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            int size = dis.readInt();
            Resume resume = new Resume(uuid, fullName);
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }
            size = dis.readInt();
            for (int i = 0; i < size; i++) {

                SectionType type = SectionType.valueOf(dis.readUTF());
                switch (type) {
                    case OBJECTIVE, PERSONAL -> resume.addSection(type, new TextSection(dis.readUTF()));
                    case ACHIEVEMENT, QUALIFICATION -> {
                        int listSize = dis.readInt();
                        List<String> list = new ArrayList<>();
                        for (int j = 0; j < listSize; j++) {
                            list.add(dis.readUTF());
                        }
                        resume.addSection(type, new ListSection(list));
                    }
                    case EXPERIENCE, EDUCATION -> {
                        int orgSize = dis.readInt();
                        List<Organisation> organisations = new ArrayList<>();
                        List<Organisation.Position> positions = new ArrayList<>();
                        for (int k = 0; k < orgSize; k++) {
                            String company = dis.readUTF();
                            URL url = null;
                            String sUrl = dis.readUTF();
                            if (!sUrl.equals("null")) {
                                url = new URL(sUrl);
                            }
                            int posSize = dis.readInt();
                            for (int l = 0; l < posSize; l++) {
                                YearMonth started = YearMonth.parse(dis.readUTF());
                                YearMonth finished = YearMonth.parse(dis.readUTF());
                                String title = dis.readUTF();
                                String description = dis.readUTF();
                                positions.add(new Organisation.Position(started, finished, title, description));
                            }
                            organisations.add(new Organisation(company, url, positions));
                        }
                        resume.addSection(type, new OrganisationSection(organisations));
                    }
                }
            }
            return resume;
        }
    }

}
