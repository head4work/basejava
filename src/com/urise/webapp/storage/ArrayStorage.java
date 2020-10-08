package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10_000];
    public int size = 0;


    public void update(Resume r, Resume updt) {
        if (resumePresent(r.getUuid()) >= 0) {
            storage[resumePresent(r.getUuid())].setUuid(updt.getUuid());
        } else System.out.println("ERROR: such uuid doesn't exist ");
    }

    public void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
    }

    public void save(Resume r) {
        if (resumePresent(r.getUuid()) < 0) {
            storage[size] = r;
            if (size < storage.length) {
                size++;
            }
        } else System.out.println("ERROR: such uuid already exist ");
    }

    public Resume get(String uuid) {
        if (resumePresent(uuid) >= 0) {
            return storage[resumePresent(uuid)];
        } else System.out.println("ERROR: such uuid doesn't exist ");
        return null;
    }

    public void delete(String uuid) {
        if (resumePresent(uuid) >= 0) {
            if (size - 1 - resumePresent(uuid) >= 0) {
                System.arraycopy(storage, resumePresent(uuid) + 1, storage, resumePresent(uuid),
                        size - 1 - resumePresent(uuid));
            }
            size--;
        } else System.out.println("ERROR: such uuid doesn't exist ");
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        Resume[] resumes = new Resume[size];
        if (resumes.length >= 0) System.arraycopy(storage, 0, resumes, 0, resumes.length);
        return resumes;
    }

    public int size() {
        return size;
    }

    private int resumePresent(String uuid) {
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
