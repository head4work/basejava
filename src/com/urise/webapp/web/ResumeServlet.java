package com.urise.webapp.web;

import com.urise.webapp.model.*;
import com.urise.webapp.storage.Storage;
import com.urise.webapp.util.Config;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ResumeServlet extends HttpServlet {
    private Storage storage;

    @Override
    public void init() throws ServletException {
        storage = Config.get().getStorage();
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        if (uuid.equals("")) {
            Resume r = new Resume(fullName);
            uuid = r.getUuid();
            storage.save(r);
        }
        Resume r = storage.get(uuid);
        r.setFullName(fullName);
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                r.addContact(type, value);
            } else {
                r.getContacts().remove(type);
            }
        }
        request.getParameterMap().forEach((s, strings) -> {
            switch (s) {
                case "OBJECTIVE", "PERSONAL":
                   r.addSection(SectionType.valueOf(s), new TextSection(String.join(" ", strings)));
                    break;
                case "ACHIEVEMENT", "QUALIFICATION":
                    if (strings.length != 0) {
                        String string = String.join("", strings).trim();
                        r.addSection(SectionType.valueOf(s), new ListSection(Stream.of(string.split("\n"))
                                .filter(s1 -> s1.length() > 1)
                                .collect(Collectors.toList())));
                    } else {
                        r.getSections().remove(SectionType.valueOf(s));
                    }
                    break;
            }
        });

        storage.update(r);
        response.sendRedirect("resume");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");

        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume r;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view", "edit":
                r = storage.get(uuid);
                break;
            case "new":
                r = new Resume();
                r.addEmptySections();
                break;
            default:
                throw new IllegalStateException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp").forward(request, response);
    }
}