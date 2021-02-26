<%@ page import="com.urise.webapp.model.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.urise.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}"></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>

        <h3>Секции:</h3>
        <c:forEach var="sectionEntry" items="<%=resume.getSections()%>">

            <jsp:useBean id="sectionEntry"
                         type="java.util.Map.Entry<com.urise.webapp.model.SectionType, com.urise.webapp.model.Section>"/>

            <c:choose>
                <c:when test="${sectionEntry.key eq SectionType.OBJECTIVE || sectionEntry.key eq SectionType.PERSONAL}">
                    <dl>
                        <dt><%=sectionEntry.getKey().getTitle()%>
                        </dt>
                        <dt><input type="text" name="${sectionEntry.key.name()}" size=150
                                   value="<%=((TextSection) sectionEntry.getValue()).getText()%>"></dt>
                    </dl>

                </c:when>

                <c:when test="${sectionEntry.key eq SectionType.ACHIEVEMENT || sectionEntry.key eq SectionType.QUALIFICATION}">
                    <dl>
                        <dt>
                            <label for="${sectionEntry.key.name()}">${sectionEntry.key.title}</label><br/>
                            <textarea id="${sectionEntry.key.name()}" name="${sectionEntry.key.name()}"
                                      rows="10"
                                      cols="150"><%=String.join("\n", ((ListSection) sectionEntry.getValue()).getList())%></textarea>
                        </dt>

                    </dl>
                </c:when>

                <c:when test="${sectionEntry.key eq SectionType.EDUCATION || sectionEntry.key eq SectionType.EXPERIENCE}">
                    <h4><%=sectionEntry.getKey().getTitle()%>
                    </h4>


                    <c:forEach var="organisations"
                               items="<%=((OrganisationSection) sectionEntry.getValue()).getOrganisationList()%>">
                        <jsp:useBean id="organisations"
                                     type="com.urise.webapp.model.Organisation"/>
                        <dl>
                            <dt>Company:<br>
                                <input type="text" name="${sectionEntry.key.name()}" size=150
                                       value="${organisations.company}">
                            </dt>
                            <dt>homepage: <br> <input type="text" name="${sectionEntry.key.name()}" size=150
                                                      value="${organisations.homepage}"></dt>
                            <input type="hidden" name="${sectionEntry.key.name()}" value="${organisations.hashCode()}">
                            <c:forEach var="positions" items="<%=organisations.getPosition()%>">
                                <jsp:useBean id="positions" type="com.urise.webapp.model.Organisation.Position"/>
                                <b>Позиции:</b>
                                <dt>startDate:<br> <input type="text" name="${organisations.hashCode()}" size=150
                                                          value="${positions.startDate}"></dt>
                                <dt>finishDate:<br> <input type="text" name="${organisations.hashCode()}" size=150
                                                           value="${positions.finishDate}"></dt>
                                <dt>title: <br> <input type="text" name="${organisations.hashCode()}" size=150
                                                       value="${positions.title}"></dt>
                                <dt>description:<br> <input type="text" name="${organisations.hashCode()}" size=150
                                                            value="${positions.description}"></dt>
                            </c:forEach>
                            <b>Добавить позицию:</b>
                            <dt>startDate:<br> <input type="text" name="${organisations.hashCode()}" size=150
                                                      value=""></dt>
                            <dt>finishDate:<br> <input type="text" name="${organisations.hashCode()}" size=150
                                                       value=""></dt>
                            <dt>title: <br> <input type="text" name="${organisations.hashCode()}" size=150
                                                   value=""></dt>
                            <dt>description:<br> <input type="text" name="${organisations.hashCode()}" size=150
                                                        value=""></dt>
                        </dl>


                    </c:forEach>
                    <h4>Добавить новую организацию</h4>

                    <dl>
                        <dt>Company:<br>
                            <input type="text" name="${sectionEntry.key.name()}" size=150
                                   value="">
                        </dt>
                        <dt>homepage: <br> <input type="text" name="${sectionEntry.key.name()}" size=150
                                                  value=""></dt>
                        <input type="hidden" name="${sectionEntry.key.name()}" value="new">

                        <b>Добавить позицию:</b>
                        <dt>startDate:<br> <input type="text" name="new" size=150
                                                  value=""></dt>
                        <dt>finishDate:<br> <input type="text" name="new" size=150
                                                   value=""></dt>
                        <dt>title: <br> <input type="text" name="new" size=150
                                               value=""></dt>
                        <dt>description:<br> <input type="text" name="new" size=150
                                                    value=""></dt>
                    </dl>

                </c:when>
            </c:choose>
        </c:forEach>

        <hr>
        <button type="submit">Сохранить</button>
    </form>
    <button onclick="window.history.back()">Отменить</button>

</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
