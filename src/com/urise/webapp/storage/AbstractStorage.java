package com.urise.webapp.storage;

import com.urise.webapp.exeption.ExistStorageException;
import com.urise.webapp.exeption.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage<T> implements Storage {

    public void save(Resume resume) {
        T key = searchKey(resume.getUuid());
        if (checkResumeExist(key)) {
            throw new ExistStorageException(resume.getUuid());
        } else {
            insertResume(resume);
        }
    }

    public void delete(Resume resume) {
        T key = searchKey(resume.getUuid());
        if (!checkResumeExist(key)) {
            throw new NotExistStorageException(resume.getUuid());
        } else {
            removeResume(searchKey(resume.getUuid()));
        }
    }

    public Resume get(Resume resume) {
        T key = searchKey(resume.getUuid());
        if (!checkResumeExist(key)) {
            throw new NotExistStorageException(resume.getUuid());
        } else {
            return getResume(searchKey(resume.getUuid()));
        }
    }

    public void update(Resume resume) {
        T key = searchKey(resume.getUuid());
        if (!checkResumeExist(key)) {
            throw new NotExistStorageException(resume.getUuid());
        } else {
            updateResume(resume, searchKey(resume.getUuid()));
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public abstract Resume[] getAll();

    public abstract int size();

    public abstract void updateResume(Resume resume, Object key);

    public abstract Resume getResume(T key);

    protected abstract boolean checkResumeExist(T key);

    protected abstract T searchKey(String uuid);

    protected abstract void insertResume(Resume resume);

    protected abstract void removeResume(T key);

    public abstract void clear();


}
