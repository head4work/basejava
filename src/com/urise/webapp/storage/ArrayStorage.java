package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected void removeResume(Object key) {
        storage[(Integer) key] = storage[size - 1];
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected void insertResume(Resume resume) {
        checkStorageSize(resume);
        storage[size] = resume;
        size++;
    }

    @Override
    protected Integer keySearch(String uuid) {
        int resumePresent = -1;
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                resumePresent = i;
                break;
            }
        }
        return resumePresent;
    }
}
