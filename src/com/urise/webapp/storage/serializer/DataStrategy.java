package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;

import java.io.*;
import java.net.URL;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStrategy implements SerializeStrategy {

    @Override
    public void writeResume(Resume resume, OutputStream outputStream) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(outputStream)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            Map<ContactType, String> contacts = resume.getContacts();
            dos.writeInt(contacts.size());

            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }
            Map<SectionType, Section> sections = resume.getSections();
            dos.writeInt(sections.size());

            for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
                String sectionName = entry.getKey().name();
                dos.writeUTF(sectionName);

                switch (SectionType.valueOf(sectionName)) {
                    case OBJECTIVE, PERSONAL -> {
                        TextSection text = (TextSection) entry.getValue();
                        dos.writeUTF(text.getText());
                    }
                    case ACHIEVEMENT, QUALIFICATION -> {
                        ListSection list = (ListSection) entry.getValue();
                        dos.writeInt(list.getList().size());
                        for (String s : list.getList()) {
                            dos.writeUTF(s);
                        }
                    }
                    case EXPERIENCE, EDUCATION -> {
                        OrganisationSection organisations = (OrganisationSection) entry.getValue();
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
