package com.urise.webapp.storage;

import com.urise.webapp.exeption.ExistStorageException;
import com.urise.webapp.exeption.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage<T> implements Storage {

    public void save(Resume resume) {
        T key = searchKey(resume.getUuid());
        if (!checkResumeExist(key)) {
            insertResume(resume);
        } else {
            throw new ExistStorageException(resume.getUuid());
        }
    }

    public void delete(String uuid) {
        T key = searchKey(uuid);
        if (checkResumeExist(key)) {
            removeResume(searchKey(uuid));
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    public Resume get(String uuid) {
        T key = searchKey(uuid);
        if (checkResumeExist(key)) {
            return getResume(searchKey(uuid));
        }
        throw new NotExistStorageException(uuid);
    }

    public abstract void clear();

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public abstract Resume[] getAll();

    public void update(Resume resume) {
        T key = searchKey(resume.getUuid());
        if (checkResumeExist(key)) {
            updateResume(resume, searchKey(resume.getUuid()));
        } else {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    public abstract int size();

    public abstract void updateResume(Resume resume, Object key);

    public abstract Resume getResume(T key);

    protected abstract boolean checkResumeExist(T key);

    protected abstract T searchKey(String uuid);

    protected abstract void insertResume(Resume resume);

    protected abstract void removeResume(T key);

}
