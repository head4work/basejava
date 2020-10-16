package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 100_000;

    public Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public void save(Resume resume) {
        if (getIndex(resume.getUuid()) < 0) {
            if (size < STORAGE_LIMIT) {
                saveMeta(resume);
                size++;
            } else {
                System.out.println("ERROR: Storage is full.");
            }
        } else {
            System.out.println("ERROR: Resume with uuid (" + resume.getUuid() + ") already exist.");
        }
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            deleteMeta(index);
            size--;
        } else {
            System.out.println("ERROR: Resume with uuid (" + uuid + ") doesn't exist.");
        }
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            return storage[index];
        }
        System.out.println("ERROR: Resume with uuid (" + uuid + ") doesn't exist.");
        return null;
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

    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            storage[index] = resume;
        } else {
            System.out.println("ERROR: Resume with uuid (" + resume.getUuid() + ") doesn't exist.");
        }
    }

    public int size() {
        return size;
    }

    protected abstract int getIndex(String uuid);

    protected abstract void saveMeta(Resume resume);

    protected abstract void deleteMeta(int index);

}
