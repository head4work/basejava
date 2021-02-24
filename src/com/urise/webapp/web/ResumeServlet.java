package com.urise.webapp.web;

import com.urise.webapp.model.*;
import com.urise.webapp.storage.Storage;
import com.urise.webapp.util.Config;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
        Map<String, String[]> parameterMap = request.getParameterMap();
        parameterMap.forEach((s, strings) -> {
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
                case "EXPERIENCE", "EDUCATION":
                    List<Organisation> organisationList = new ArrayList<>();
                    for (int i = 0; i < strings.length; i += 3) {
                        List<Organisation.Position> positionList = new ArrayList<>();
                        String company = strings[i];
                        URL url = null;
                        try {
                            url = new URL(strings[i + 1]);
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                        String hash = strings[i + 2];
                        String[] position = parameterMap.get(hash);
                        for (int j = 0; j < position.length; j += 3) {
                            YearMonth start = YearMonth.parse(position[j]);
                            YearMonth finish = (position[j + 1].equals("Current time")) ? YearMonth.now() : YearMonth.parse(position[j + 1]);
                            String title = position[j + 2];
                            String description = position[j + 3];
                            j++;
                            positionList.add(new Organisation.Position(start, finish, title, description));
                        }
                        organisationList.add(new Organisation(company, url, positionList));

                    }
                    r.addSection(SectionType.valueOf(s), new OrganisationSection(organisationList));
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