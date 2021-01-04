package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;

import java.io.*;
import java.net.URL;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;


public class DataStrategy implements SerializeStrategy {

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
                                writeDate(dos, position.getStartDate());
                                writeDate(dos, position.getFinishDate());
                                dos.writeUTF(position.getTitle());
                                if (position.getDescription() == null) {
                                    dos.writeUTF("");
                                } else {
                                    dos.writeUTF(position.getDescription());
                                }
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
            Resume resume = new Resume(uuid, fullName);
            readWithException(dis, () -> resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));
            readWithException(dis, () -> {
                SectionType type = SectionType.valueOf(dis.readUTF());
                switch (type) {
                    case OBJECTIVE, PERSONAL -> resume.addSection(type, new TextSection(dis.readUTF()));
                    case ACHIEVEMENT, QUALIFICATION -> {
                        List<String> list = new ArrayList<>();
                        readWithException(dis, () -> list.add(dis.readUTF()));
                        resume.addSection(type, new ListSection(list));
                    }
                    case EXPERIENCE, EDUCATION -> {
                        List<Organisation> organisations = new ArrayList<>();
                        List<Organisation.Position> positions = new ArrayList<>();
                        readWithException(dis, () -> {
                            String company = dis.readUTF();
                            URL url = null;
                            String sUrl = dis.readUTF();
                            if (!sUrl.equals("null")) {
                                url = new URL(sUrl);
                            }
                            readWithException(dis, () -> {
                                YearMonth started = readDate(dis);
                                YearMonth finished = readDate(dis);
                                String title = dis.readUTF();
                                String description = dis.readUTF();
                                if (description.equals("")) {
                                    description = null;
                                }
                                positions.add(new Organisation.Position(started, finished, title, description));
                            });
                            organisations.add(new Organisation(company, url, positions));
                        });
                        resume.addSection(type, new OrganisationSection(organisations));
                    }
                }
            });
            return resume;
        }
    }

    private interface Writer<T> {
        void accept(T t) throws IOException;
    }

    private <T> void writeWithException(DataOutputStream dos, Collection<T> collection, Writer<T> write) throws IOException {
        Objects.requireNonNull(write);
        dos.writeInt(collection.size());
        for (T element : collection) {
            write.accept(element);
        }
    }

    private void writeDate(DataOutputStream dos, YearMonth date) throws IOException {
        dos.writeUTF(date.format(DateTimeFormatter.ofPattern("uuuu-MM")));
    }


    private void readWithException(DataInputStream dis, Read runnable) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            runnable.run();
        }
    }

    private interface Read {
        void run() throws IOException;
    }

    private YearMonth readDate(DataInputStream dis) throws IOException {
        return YearMonth.parse(dis.readUTF());
    }
}
