package com.urise.webapp.storage;

import com.urise.webapp.exeption.ExistStorageException;
import com.urise.webapp.exeption.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

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

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (checkResumeExist(new Resume(uuid))) {
            return getResume(index);
        }
        throw new NotExistStorageException(uuid);
    }

    public abstract void clear();

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public abstract Resume[] getAll();

    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (checkResumeExist(resume)) {
            updateResume(resume, index);
        } else {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    public abstract int size();

    public abstract void deleteResume(int index);

    public abstract void updateResume(Resume resume, int index);

    public abstract Resume getResume(int index);

    protected abstract void saveResume(Resume resume, int index);

    protected abstract boolean checkResumeExist(Resume resume);

    protected abstract int getIndex(String uuid);

    protected abstract void insertResume(Resume resume, int index);

    protected abstract void removeResume(int index);

}
