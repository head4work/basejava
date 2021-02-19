package com.urise.webapp.model;

import com.urise.webapp.util.YearMonthAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serial;
import java.io.Serializable;
import java.net.URL;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Organisation implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String company;
    private URL homepage;
    private List<Position> position;

    public Organisation() {
    }

    public Organisation(String company, URL homepage, Position... positions) {
        this(company, homepage, Arrays.asList(positions));
    }

    public Organisation(String company, URL homepage, List<Position> position) {
        Objects.requireNonNull(company, "company should be not null");
        this.company = company;
        this.homepage = homepage;
        this.position = position;
    }

    public void setHomepage(URL homepage) {
        this.homepage = homepage;
    }

    public List<Position> getPosition() {
        return position;
    }

    public URL getHomepage() {
        return homepage;
    }

    public String getUrlOfHomepage() {
        return "<a href='" + homepage + "'>" + homepage + "</a>";
    }

    public String getCompany() {
        return company;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organisation that = (Organisation) o;
        return company.equals(that.company) &&
                Objects.equals(homepage, that.homepage) &&
                position.equals(that.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(company, homepage, position);
    }

    @Override
    public String toString() {
        return (company + "\n" +
                homepage + "\n" +
                position).replace("[", "").replace("]", "").replace(",", "")
                ;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Position implements Serializable {
        @Serial
        private static final long serialVersionUID = 1L;
        @XmlJavaTypeAdapter(YearMonthAdapter.class)
        private YearMonth startDate;
        @XmlJavaTypeAdapter(YearMonthAdapter.class)
        private YearMonth finishDate;
        private String title;
        private String description;

        public Position() {
        }

        public Position(YearMonth startDate, YearMonth finishDate, String title, String description) {
            Objects.requireNonNull(startDate, "started must not be null");
            Objects.requireNonNull(finishDate, "finished must not be null");
            Objects.requireNonNull(title, "title must not be null");
            this.startDate = startDate;
            this.finishDate = finishDate;
            this.title = title;
            this.description = description;
        }

        public YearMonth getStartDate() {
            return startDate;
        }

        public String showStartDate() {
            return startDate.format(DateTimeFormatter.ofPattern("MM/yyyy"));
        }

        public String showFinishDate() {
            return (finishDate.equals(YearMonth.now())) ? "Current time" : finishDate.format(DateTimeFormatter.ofPattern("MM/yyyy"));
        }

        public YearMonth getFinishDate() {
            return finishDate;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Position position = (Position) o;
            return Objects.equals(startDate, position.startDate) &&
                    Objects.equals(finishDate, position.finishDate) &&
                    Objects.equals(title, position.title) &&
                    Objects.equals(description, position.description);
        }

        @Override
        public int hashCode() {
            return Objects.hash(startDate, finishDate, title, description);
        }

        @Override
        public String toString() {
            return (startDate + " - " + finishDate + " " + title + " " +
                    description + "\n").replace("[", "").replace("]", "").replace(",", "")
                    ;
        }
    }
}
