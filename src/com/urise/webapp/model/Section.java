package com.urise.webapp.model;

import java.io.Serializable;

abstract public class Section<T> implements Serializable {
    private T content;

    public Section() {
    }

    public Section(T content) {
        this.content = content;
    }

    public T getContent() {
        return content;
    }


}
