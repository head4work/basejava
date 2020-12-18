package com.urise.webapp.util;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.YearMonth;

public class YearMonthAdapter extends XmlAdapter<String, YearMonth> {

    @Override
    public YearMonth unmarshal(String s) {
        return YearMonth.parse(s);
    }

    @Override
    public String marshal(YearMonth yearMonth) {
        return yearMonth.toString();
    }
}
