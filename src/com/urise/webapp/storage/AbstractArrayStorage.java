package com.urise.webapp.storage;

import com.urise.webapp.exeption.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10_000;
    protected static Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;


    @Override
    protected boolean checkResumeExist(Resume resume) {
        return keySearch(resume.getUuid()) >= 0;
    }



    public Resume getResume(Object key) {
        return storage[(Integer) key];
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);

    }

    @Override
    public void updateResume(Resume resume, int index) {
        storage[index] = resume;
    }

    public int size() {
        return size;
    }

    protected abstract Integer keySearch(String uuid);

    protected abstract void insertResume(Resume resume);

    protected void checkStorageSize(Resume resume) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage is full.", resume.getUuid());
        }
    }



}
