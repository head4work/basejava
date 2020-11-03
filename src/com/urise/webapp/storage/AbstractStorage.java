package com.urise.webapp.storage;

import com.urise.webapp.exeption.ExistStorageException;
import com.urise.webapp.exeption.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage<T> implements Storage {

    public void save(Resume resume) {
        T key = returnKeyIfResumeNotExist(resume);
        saveResume(resume, key);
    }

    public void delete(String uuid) {
        T key = returnKeyIfResumeExist(uuid);
        deleteResume(key);
    }

    public Resume get(String uuid) {
        T key = returnKeyIfResumeExist(uuid);
        return getResume(key);
    }

    public void update(Resume resume) {
        T key = returnKeyIfResumeExist(resume.getUuid());
        updateResume(resume, key);
    }

    private T returnKeyIfResumeExist(String uuid) {
        T key = searchKey(uuid);
        if (!checkResumeExist(key)) {
            throw new NotExistStorageException(uuid);
        }
        return key;
    }

    private T returnKeyIfResumeNotExist(Resume resume) {
        T key = searchKey(resume.getUuid());
        if (checkResumeExist(key)) {
            throw new ExistStorageException(resume.getUuid());
        }
        return key;
    }

    protected abstract void deleteResume(T key);

    protected abstract void saveResume(Resume resume, T key);

    public abstract void updateResume(Resume resume, Object key);

    public abstract Resume getResume(T key);

    protected abstract boolean checkResumeExist(T key);

    protected abstract T searchKey(String uuid);

}
