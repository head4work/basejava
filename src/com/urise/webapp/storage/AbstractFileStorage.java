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
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            list.add(getResume(file));
        }
        return list;
    }

    @Override
    protected void deleteResume(File file) {
        if (!file.delete()) {
            throw new StorageException("File delete error", file.getName());
        }
    }

    @Override
    protected void saveResume(Resume resume, File file) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new StorageException("Couldn't create file " + file.getAbsolutePath(), file.getName(), e);
        }
        updateResume(resume, file);
    }

    @Override
    public void updateResume(Resume resume, File file) {
        try {
            writeResume(resume, file);
        } catch (Exception e) {
            throw new StorageException("File write error", resume.getUuid(), e);
        }
    }

    @Override
    public Resume getResume(File file) {
        try {
            return readResume(file);
        } catch (Exception e) {
            throw new StorageException("File read error ", file.getName(), e);
        }
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
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            deleteResume(file);
        }
    }

    @Override
    public int size() {
        return Objects.requireNonNull(directory.list()).length;
    }

    protected abstract void writeResume(Resume resume, File file);

    protected abstract Resume readResume(File file);
}
