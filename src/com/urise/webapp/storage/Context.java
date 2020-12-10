package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Context {
    ResumeSerializeStrategy strategy;

    public Context(ResumeSerializeStrategy strategy) {
        this.strategy = strategy;
    }

    public void executeSave(Resume resume, OutputStream outputStream) throws IOException {
        strategy.writeResume(resume, outputStream);
    }

    public Resume executeRead(InputStream inputStream) throws IOException {
        return strategy.readResume(inputStream);
    }
}
