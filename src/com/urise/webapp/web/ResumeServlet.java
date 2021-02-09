package com.urise.webapp.web;

import com.urise.webapp.exeption.StorageException;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import com.urise.webapp.model.Section;
import com.urise.webapp.model.SectionType;
import com.urise.webapp.storage.Storage;
import com.urise.webapp.util.Config;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class ResumeServlet extends HttpServlet {
    private Storage storage;

    @Override
    public void init() throws ServletException {
        storage = Config.get().getStorage();
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String uuid = request.getParameter("uuid");

        initHeader(response);
        if (uuid == null) {
            storage.getAllSorted().forEach(resume -> {
                try {
                    buildTable(response, resume);
                } catch (IOException e) {
                    throw new StorageException(e.getMessage());
                }
            });
        } else {
            Resume resume = storage.get(uuid);
            buildTable(response, resume);
        }
        initFooter(response);
    }

    private void buildTable(HttpServletResponse response, Resume resume) throws IOException {
        insertResume(response, resume);
        insertContacts(response, resume);
        insertSections(response, resume);
    }

    private void initFooter(HttpServletResponse response) throws IOException {
        response.getWriter().write(

                "</table>" +
                        "" +
                        "</body>" +
                        "</html>");
    }

    private void insertSections(HttpServletResponse response, Resume resume) throws IOException {
        StringBuilder sections = new StringBuilder();
        for (Map.Entry<SectionType, Section> e : resume.getSections().entrySet()) {
            sections.append(e.getKey().name()).append(" : ").append(e.getValue()).append("\n");
        }
        response.getWriter().write(
                "    <td>" + sections + " </td>" +
                        "  </tr>");
    }

    private void insertContacts(HttpServletResponse response, Resume resume) throws IOException {
        StringBuilder contacts = new StringBuilder();
        for (Map.Entry<ContactType, String> e : resume.getContacts().entrySet()) {
            contacts.append(e.getKey().name()).append(" : ").append(e.getValue()).append("\n");
        }
        response.getWriter().write("<td> " + contacts.toString() + "</td>");
    }

    private void insertResume(HttpServletResponse response, Resume resume) throws IOException {
        response.getWriter().write(
                "  <tr>" +
                        "    <td> " + resume.getUuid() + "</td>" +
                        "    <td>" + resume.getFullName() + "</td>");
    }

    private void initHeader(HttpServletResponse response) throws IOException {
        response.getWriter().write("<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "<style>" +
                "table, th, td {" +
                "  border: 1px solid black;" +
                "border-collapse: collapse;" +
                "}" +
                "th, td {" +
                "  padding: 15px;" +
                "}" +
                "</style>" +
                "</head>" +
                "<body>" +
                "" +
                "<h2>Resume</h2>" +
                "" +
                "<table style=\"width:100%\">" +
                "  <tr>" +
                "    <th>UUID</th>" +
                "    <th>Full Name</th> " +
                "    <th>Contacts</th> " +
                "    <th>Sections</th> " +
                "  </tr>");
    }

}
