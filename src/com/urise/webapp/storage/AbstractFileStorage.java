package com.urise.webapp.storage;

import com.urise.webapp.exeption.StorageException;
import com.urise.webapp.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private final File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not a directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
    }

    @Override
    protected List<Resume> getResumes() {
        List<Resume> list = new ArrayList<>();
        for (File file : directory.listFiles()) {
            list.add(readResume(file));
        }
        return list;
    }

    @Override
    protected void deleteResume(File file) {
        file.delete();
    }

    @Override
    protected void saveResume(Resume resume, File file) {
        try {
            file.createNewFile();
            writeResume(resume, file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    public void updateResume(Resume resume, File file) {
        writeResume(resume, file);
    }

    @Override
    public Resume getResume(File file) {
        return readResume(file);
    }

    @Override
    protected boolean checkResumeExist(File file) {
        return file.exists();
    }

    @Override
    protected File searchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    public void clear() {
        for (File file : directory.listFiles()) {
            file.delete();
        }
    }

    @Override
    public int size() {
        return directory.list().length;
    }

    protected abstract void writeResume(Resume resume, File file);

    protected abstract Resume readResume(File file);
}
