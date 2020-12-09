package com.urise.webapp.storage;

public class ResumeFile {
    ResumeFileStrategy fileStrategy;

    public void setFileStrategy(ResumeFileStrategy fileStrategy) {
        this.fileStrategy = fileStrategy;
    }

    void executeFileSave() {
        fileStrategy.saveFile();
    }

    void executeFileRead() {
        fileStrategy.readFile();
    }
}
