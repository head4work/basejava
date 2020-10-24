package com.urise.webapp.storage;

import com.urise.webapp.exeption.ExistStorageException;
import com.urise.webapp.exeption.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class AbstractStorage implements Storage {

    protected static ArrayList<Resume> list = new ArrayList<>();
    protected static final int STORAGE_LIMIT = 10_000;
    protected static Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public void save(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (!checkResumeExist(resume)) {
            saveResume(resume, index);
        } else {
            throw new ExistStorageException(resume.getUuid());
        }
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (checkResumeExist(new Resume(uuid))) {
            deleteResume(index);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    public abstract void deleteResume(int index);

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            return storage[index];
        }
        throw new NotExistStorageException(uuid);
    }

    public void clear() {
        list.clear();
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return list.toArray(Resume[]::new);

    }

    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (checkResumeExist(resume)) {
            updateResume(resume, index);
        } else {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    public abstract void updateResume(Resume resume, int index);

    public int size() {
        return list.size();
    }

    protected abstract void saveResume(Resume resume, int index);

    protected abstract boolean checkResumeExist(Resume resume);

    protected abstract int getIndex(String uuid);

    protected abstract void injectResume(Resume resume, int index);

    protected abstract void ejectResume(int index);

}
