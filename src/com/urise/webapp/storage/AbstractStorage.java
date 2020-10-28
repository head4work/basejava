package com.urise.webapp.storage;

import com.urise.webapp.exeption.ExistStorageException;
import com.urise.webapp.exeption.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage<T> implements Storage {

    public void save(Resume resume) {
        if (!checkResumeExist(resume)) {
            insertResume(resume);
        } else {
            throw new ExistStorageException(resume.getUuid());
        }
    }

    public void delete(String uuid) {
        if (checkResumeExist(new Resume(uuid))) {
            removeResume(keySearch(uuid));
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    public Resume get(String uuid) {
        if (checkResumeExist(new Resume(uuid))) {
            return getResume(keySearch(uuid));
        }
        throw new NotExistStorageException(uuid);
    }

    public abstract void clear();

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public abstract Resume[] getAll();

    public void update(Resume resume) {
        if (checkResumeExist(resume)) {
            updateResume(resume, keySearch(resume.getUuid()));
        } else {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    public abstract int size();

    public abstract void updateResume(Resume resume, Object key);

    public abstract Resume getResume(T key);

    protected abstract boolean checkResumeExist(Resume resume);

    protected abstract T keySearch(String uuid);

    protected abstract void insertResume(Resume resume);

    protected abstract void removeResume(T key);

}
