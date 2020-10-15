package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            System.arraycopy(storage, index + 1, storage, index, size - 1 - index);
            size--;
        } else {
            System.out.println("ERROR: Resume with uuid (" + uuid + ") doesn't exist.");
        }
    }
    protected void saveMeta(Resume resume) {
        storage[size] = resume;
        size++;
    }

    protected int getIndex(String uuid) {
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
