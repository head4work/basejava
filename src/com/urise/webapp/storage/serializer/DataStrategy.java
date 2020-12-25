package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;

import java.io.*;
import java.net.URL;
import java.time.YearMonth;
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
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue().getClass().getSimpleName());

                switch (entry.getValue().getClass().getSimpleName()) {
                    case "TextSection" -> {
                        TextSection text = (TextSection) entry.getValue();
                        dos.writeUTF(text.getText());
                    }
                    case "ListSection" -> {
                        ListSection list = (ListSection) entry.getValue();
                        dos.writeInt(list.getList().size());
                        for (String s : list.getList()) {
                            dos.writeUTF(s);
                        }
                    }
                    case "OrganisationSection" -> {
                        OrganisationSection org = (OrganisationSection) entry.getValue();
                        dos.writeInt(org.getOrganisationList().size());
                        for (Organisation o : org.getOrganisationList()) {
                            dos.writeUTF(o.getCompany());
                            dos.writeUTF(String.valueOf(o.getHomepage()));
                            dos.writeInt(o.getPosition().size());
                            for (Organisation.Position p : o.getPosition()) {
                                dos.writeUTF(p.getStartDate().toString());
                                dos.writeUTF(p.getFinishDate().toString());
                                dos.writeUTF(p.getTitle());
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
                switch (dis.readUTF()) {
                    case "TextSection" -> resume.addSection(type, new TextSection(dis.readUTF()));
                    case "ListSection" -> {
                        int lsize = dis.readInt();
                        List<String> list = new ArrayList<>();
                        for (int j = 0; j < lsize; j++) {
                            list.add(dis.readUTF());
                        }
                        resume.addSection(type, new ListSection(list));
                    }
                    case "OrganisationSection" -> {
                        int orgsize = dis.readInt();
                        List<Organisation> orgs = new ArrayList<>();
                        List<Organisation.Position> positions = new ArrayList<>();
                        for (int k = 0; k < orgsize; k++) {
                            String company = dis.readUTF();
                            URL url = new URL(dis.readUTF());
                            int possize = dis.readInt();
                            for (int l = 0; l < possize; l++) {
                                YearMonth started = YearMonth.parse(dis.readUTF());
                                YearMonth finished = YearMonth.parse(dis.readUTF());
                                String title = dis.readUTF();
                                String description = dis.readUTF();
                                positions.add(new Organisation.Position(started, finished, title, description));
                            }
                            orgs.add(new Organisation(company, url, positions));
                        }
                        resume.addSection(type, new OrganisationSection(orgs));
                    }
                }
            }

            return resume;
        }
    }

}
