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
    protected void saveResume(Resume resume) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage is full.", resume.getUuid());
        } else {
            insertResume(resume);
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

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    @Override
    public void updateResume(Resume resume, Object key) {
        storage[(int) key] = resume;
    }

    @Override
    public int size() {
        return size;
    }

}
