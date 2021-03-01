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
                            <dt><label for="${sectionEntry.key.name()}">Company, web address</label><br/>
                                <textarea id="${sectionEntry.key.name()}" name="${sectionEntry.key.name()}"
                                          rows="2"
                                          cols="150">${organisations.company}<%='\n'%>
                                    ${organisations.homepage}</textarea></dt>

                            <input type="hidden" name="${sectionEntry.key.name()}" value="${organisations.hashCode()}">
                            <c:forEach var="positions" items="<%=organisations.getPosition()%>">
                                <jsp:useBean id="positions" type="com.urise.webapp.model.Organisation.Position"/>
                                <b>Позиции:</b>
                                <dt>Dates:<br> <input type="text" name="${organisations.hashCode()}" size=150
                                                      value="${positions.startDate} - ${positions.finishDate}"></dt>
                                <dt>
                                    <label for="${organisations.hashCode()}">Title, description</label><br/>
                                    <textarea id="${organisations.hashCode()}" name="${organisations.hashCode()}"
                                              rows="2"
                                              cols="150">${positions.title}<%='\n'%>
                                        ${positions.description}</textarea></dt>

                            </c:forEach>
                            <b>Добавить позицию:</b>
                            <dt>Dates:<br> <input type="text" name="${organisations.hashCode()}" size=150
                                                  placeholder="0000-00 - 0000-00"></dt>
                            <dt>
                                <label for="${organisations.hashCode()}">Title, description</label><br/>
                                <textarea id="${organisations.hashCode()}" name="${organisations.hashCode()}"
                                          rows="2"
                                          cols="150" placeholder="Title<%='\n'%>Description"></textarea>
                            </dt>
                        </dl>

                    </c:forEach>
                    <h4>Добавить новую организацию</h4>
                    <dl>
                        <dt><label for="${sectionEntry.key.name()}">Company, web address</label><br/>
                            <textarea id="${sectionEntry.key.name()}" name="${sectionEntry.key.name()}"
                                      rows="2"
                                      cols="150" placeholder="Company<%='\n'%>Web address"></textarea></dt>

                        <input type="hidden" name="${sectionEntry.key.name()}"
                               value="${sectionEntry.key.name().hashCode()}">

                        <b>Добавить позицию:</b>
                        <dt>Dates:<br> <input type="text" name="${sectionEntry.key.name().hashCode()}" size=150
                                              placeholder="0000-00 - 0000-00"></dt>
                        <dt>
                            <label for="${sectionEntry.key.name().hashCode()}">Title, description</label><br/>
                            <textarea id="${sectionEntry.key.name().hashCode()}"
                                      name="${sectionEntry.key.name().hashCode()}"
                                      rows="2"
                                      cols="150" placeholder="Title<%='\n'%>Description"></textarea>
                        </dt>
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
