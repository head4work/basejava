package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {


    @Override
    protected void insertResume(Resume resume) {
        storage[size] = resume;
    }

    @Override
    protected void removeResume(Integer key) {
        storage[key] = storage[size - 1];
    }

    @Override
    protected Integer searchKey(String uuid) {
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
