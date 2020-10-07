package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    public int size = 0;

    public boolean resume_Present(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return true;
            }
        }
        return false;
    }

    public void update(Resume r) {

    }

    public void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
    }

    public void save(Resume r) {
        if (!resume_Present(r.getUuid())) {
            storage[size] = r;
            size++;
        } else System.out.println("ERROR: such uuid already exist ");
    }

    public Resume get(String uuid) {
        if (resume_Present(uuid)) {
            for (int i = 0; i < size; i++) {
                if (storage[i].getUuid().equals(uuid)) {
                    return storage[i];
                }
            }
        } else System.out.println("ERROR: such uuid doesn't exist ");
        return null;
    }

    public void delete(String uuid) {
        if (resume_Present(uuid)) {
            for (int i = 0; i < size; i++) {
                if (storage[i].getUuid().equals(uuid)) {
                    for (int j = i; j < size - 1; j++) {
                        storage[j] = storage[j + 1];
                    }
                    size--;
                }
            }
        } else System.out.println("ERROR: such uuid doesn't exist ");
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        Resume[] resumes = new Resume[size];
        for (int i = 0; i < resumes.length; i++) {
            resumes[i] = storage[i];
        }
        return resumes;
    }

    public int size() {
        return size;
    }
}
