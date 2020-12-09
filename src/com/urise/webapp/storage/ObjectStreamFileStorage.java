package com.urise.webapp.storage;

import com.urise.webapp.exeption.StorageException;
import com.urise.webapp.model.Resume;

import java.io.*;

public class ObjectStreamFileStorage extends AbstractFileStorage implements ResumeFileStrategy {
    protected ObjectStreamFileStorage(File directory) {
        super(directory);
    }

    @Override
    protected void writeResume(Resume resume, OutputStream outputStream) throws IOException {
        try (ObjectOutputStream os = new ObjectOutputStream(outputStream)) {
            os.writeObject(resume);
        }
    }

    @Override
    protected Resume readResume(InputStream inputStream) throws IOException {
        try (ObjectInputStream ois = new ObjectInputStream(inputStream)) {
            return (Resume) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new StorageException("Error read resume", null, e);
        }
    }


    @Override
    public void saveFile() {

    }

    @Override
    public void readFile() {

    }
}
