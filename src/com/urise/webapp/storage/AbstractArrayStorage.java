package com.urise.webapp.storage;

import com.urise.webapp.exeption.NotExistStorageException;
import com.urise.webapp.exeption.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {
    
    @Override
    protected void saveResume(Resume resume, int index) {
        if (size < STORAGE_LIMIT) {
            injectResume(resume, index);
            size++;
        } else {
            throw new StorageException("Storage is full.", resume.getUuid());
        }
    }

    @Override
    protected boolean checkResumeExist(Resume resume) {
        return getIndex(resume.getUuid()) >= 0;
    }

   /* public void delete(String uuid) {
        int index = getIndex(uuid);
        if (!checkResumeExist(new Resume(uuid))) {
            ejectResume(index);
            storage[size - 1] = null;
            size--;
        } else {
            throw new NotExistStorageException(uuid);
        }
    }*/
    public void deleteResume(int index) {
        ejectResume(index);
        storage[size - 1] = null;
        size--;
    }

    public Resume getResume(int index) {
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

    protected abstract int getIndex(String uuid);

    protected abstract void injectResume(Resume resume, int index);

    protected abstract void ejectResume(int index);

}
