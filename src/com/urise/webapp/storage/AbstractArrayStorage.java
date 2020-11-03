package com.urise.webapp.storage;

import com.urise.webapp.exeption.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    protected static final int STORAGE_LIMIT = 10_000;
    protected static Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    protected void saveResume(Resume resume, Integer key) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage is full.", resume.getUuid());
        } else {
            insertResume(resume, key);
            size++;
        }
    }

    @Override
    protected void deleteResume(Integer key) {
        removeResume(key);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected boolean checkResumeExist(Integer key) {
        return key >= 0;
    }

    @Override
    public Resume getResume(Integer key) {
        return storage[key];
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    @Override
    public void updateResume(Resume resume, Object key) {
        storage[(int) key] = resume;
    }

    public int size() {
        return size;
    }

    protected abstract void removeResume(Integer key);

    protected abstract void insertResume(Resume resume, Integer key);

}
