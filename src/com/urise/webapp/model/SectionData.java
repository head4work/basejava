package com.urise.webapp.model;

import java.util.Date;

public class SectionData {
    Date value;

    @Override
    public String toString() {
        return "SectionData{" +
                "value=" + value +
                '}';
    }

    public SectionData(Date value) {
        this.value = value;
    }
}

