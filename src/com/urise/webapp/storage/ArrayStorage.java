package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10_000];
    private int size = 0;

    public void update(Resume r) {
        int i = resumePresent(r.getUuid());
        if (i >= 0) {
            storage[i] = r;
        } else {
            System.out.println("ERROR: such uuid doesn't exist ");
        }
    }

    public void clear() {
        Arrays.fill(storage, null);
        size = 0;
    }

    public void save(Resume resume) {
        if (resumePresent(resume.getUuid()) < 0) {
            if (size < storage.length) {
                storage[size] = resume;
                size++;
            }
        } else {
            System.out.println("ERROR: such uuid already exist ");
        }
    }

    public Resume get(String uuid) {
        int i = resumePresent(uuid);
        if (i >= 0) {
            return storage[i];
        }
        System.out.println("ERROR: such uuid doesn't exist ");
        return null;
    }


    public void delete(String uuid) {
        int i = resumePresent(uuid);
        if (i >= 0) {
            if (size - 1 - i >= 0) {
                System.arraycopy(storage, i + 1, storage, i, size - 1 - i);
            }
            size--;
        } else {
            System.out.println("ERROR: such uuid doesn't exist ");
        }
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
