package com.urise.webapp.storage;

import com.urise.webapp.exeption.ExistStorageException;
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

    public void save(Resume resume) {
        int index = keySearch(resume.getUuid());
        if (index < 0) {
            if (size < STORAGE_LIMIT) {
                saveResume(resume, index);
                size++;
            } else {
                throw new StorageException("Storage is full.", resume.getUuid());
            }
        } else {
            throw new ExistStorageException(resume.getUuid());
        }
    }
    @Override
    protected void saveResume(Resume resume, int index) {
        if (size < STORAGE_LIMIT) {
            insertResume(resume, index);
            size++;
        } else {
            throw new StorageException("Storage is full.", resume.getUuid());
        }
    }

    @Override
    protected boolean checkResumeExist(Resume resume) {
        return keySearch(resume.getUuid()) >= 0;
    }

    public void deleteResume(int index,String uuid) {
        removeResume(index,uuid);
        storage[size - 1] = null;
        size--;
    }

    public Resume getResume(int index, String uuid) {
        return storage[index];
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

    protected abstract void insertResume(Resume resume, int index);

    protected abstract void removeResume(int index,String uuid);

}
