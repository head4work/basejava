package com.urise.webapp.storage;

import com.urise.webapp.exeption.ExistStorageException;
import com.urise.webapp.exeption.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage<T> implements Storage {

    public void save(Resume resume) {
        checkExistStorageException(resume);
        saveResume(resume);
    }

    public void delete(String uuid) {
        T key = checkNotExistException(uuid);
        deleteResume(key);
    }

    public Resume get(String uuid) {
        T key = checkNotExistException(uuid);
        return getResume(key);
    }

    public void update(Resume resume) {
        T key = checkNotExistException(resume.getUuid());
        updateResume(resume, key);
    }

    private T checkNotExistException(String uuid) {
        T key = searchKey(uuid);
        if (!checkResumeExist(key)) {
            throw new NotExistStorageException(uuid);
        }
        return key;
    }

    private void checkExistStorageException(Resume resume) {
        T key = searchKey(resume.getUuid());
        if (checkResumeExist(key)) {
            throw new ExistStorageException(resume.getUuid());
        }
    }

    protected abstract void deleteResume(T key);

    protected abstract void saveResume(Resume resume);

    public abstract void clear();

    public abstract Resume[] getAll();

    public abstract int size();

    public abstract void updateResume(Resume resume, Object key);

    public abstract Resume getResume(T key);

    protected abstract boolean checkResumeExist(T key);

    protected abstract T searchKey(String uuid);

    protected abstract void insertResume(Resume resume);

    protected abstract void removeResume(T key);

}
