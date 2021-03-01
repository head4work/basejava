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


                    for (int i = 0; i < strings.length; i += 2) {
                        List<Organisation.Position> positionList = new ArrayList<>();
                        if (strings[i].trim().length() < 2 || !strings[i].contains("\n") || !strings[i].contains("http")) {
                            break;
                        }
                        String[] split = strings[i].split("\n", 2);
                        String company = split[0];
                        URL url;
                        try {
                            url = new URL(split[1]);
                        } catch (MalformedURLException e) {
                            throw new IllegalStateException(e.getMessage() + " illegal url address");
                        }
                        String hash = strings[i + 1];
                        String[] position = parameterMap.get(hash);
                        for (int j = 0; j < position.length; j += 2) {
                            if (position[j].length() < 17 || position[j + 1].trim().length() < 2) {
                                break;
                            }
                            String[] split1 = position[j].split(" - ", 2);
                            YearMonth start = YearMonth.parse(split1[0]);
                            YearMonth finish = (split1[1].equals("Current time")) ? YearMonth.now() : YearMonth.parse(split1[1]);
                            String[] split2 = position[j + 1].split("\n", 2);
                            String title = split2[0];
                            String description = split2.length < 2 ? "" : split2[1];
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