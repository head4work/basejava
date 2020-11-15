package com.urise.webapp.model;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class CompaniesListSection {
    List<CompanyContent> list;

    public CompaniesListSection(List<CompanyContent> list) {
        this.list = list;
    }
}
