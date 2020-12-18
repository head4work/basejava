package com.urise.webapp.model;

import java.io.Serial;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class OrganisationSection extends Section {
    @Serial
    private static final long serialVersionUID = 1L;

    private List<Organisation> organisationList;

    public OrganisationSection() {
    }

    public OrganisationSection(Organisation... organisations) {
        this(Arrays.asList(organisations));
    }

    public OrganisationSection(List<Organisation> organisationList) {
        this.organisationList = organisationList;
    }

    public List<Organisation> getOrganisationList() {
        return organisationList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganisationSection that = (OrganisationSection) o;
        return organisationList.equals(that.organisationList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(organisationList);
    }

    @Override
    public String toString() {
        return (organisationList + "").replace("[", "").replace("]", "").replace(",", "")
                ;
    }
}
