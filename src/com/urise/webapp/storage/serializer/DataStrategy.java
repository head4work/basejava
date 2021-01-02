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

    private <T> void writeWithException(DataOutputStream dos, Collection<T> collection, MyConsumerWriter<T> writer) throws IOException {
        Objects.requireNonNull(writer);
        dos.writeInt(collection.size());
        for (T element : collection) {
            writer.accept(element);
        }
    }

    @Override
    public void writeResume(Resume resume, OutputStream outputStream) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(outputStream)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());

            writeWithException(dos, resume.getContacts().entrySet(), contactTypeStringEntry -> {
                dos.writeUTF(contactTypeStringEntry.getKey().name());
                dos.writeUTF(contactTypeStringEntry.getValue());
            });

            writeWithException(dos, resume.getSections().entrySet(), sectionTypeSectionEntry -> {
                dos.writeUTF(sectionTypeSectionEntry.getKey().name());

                switch (SectionType.valueOf(sectionTypeSectionEntry.getKey().name())) {
                    case OBJECTIVE, PERSONAL -> {
                        TextSection text = (TextSection) sectionTypeSectionEntry.getValue();
                        dos.writeUTF(text.getText());
                    }
                    case ACHIEVEMENT, QUALIFICATION -> {
                        ListSection list = (ListSection) sectionTypeSectionEntry.getValue();
                        writeWithException(dos, list.getList(), dos::writeUTF);
                    }
                    case EXPERIENCE, EDUCATION -> {
                        OrganisationSection organisations = (OrganisationSection) sectionTypeSectionEntry.getValue();
                        writeWithException(dos, organisations.getOrganisationList(), organisation -> {
                            dos.writeUTF(organisation.getCompany());
                            dos.writeUTF(String.valueOf(organisation.getHomepage()));
                            writeWithException(dos, organisation.getPosition(), position -> {
                                dos.writeUTF(position.getStartDate().format(DateTimeFormatter.ofPattern("uuuu-MM")));
                                dos.writeUTF(position.getFinishDate().format(DateTimeFormatter.ofPattern("uuuu-MM")));
                                dos.writeUTF(position.getTitle());
                                if (position.getDescription() == null) {
                                    position.setDescription("");
                                }
                                dos.writeUTF(position.getDescription());
                            });
                        });
                    }
                }
            });
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
