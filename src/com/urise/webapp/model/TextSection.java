package com.urise.webapp.model;

public class TextSection extends Section<String>{

    public TextSection(String content) {
        super(content);
    }

    @Override
    public String toString() {
        return "SectionText{" +
                "content=" + content +
                '}';
    }

/*String text;
    public SectionText(String text) {
        this.text = text;
    }*/
}
