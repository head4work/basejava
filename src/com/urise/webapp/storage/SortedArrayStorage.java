package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;


public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void removeResume(Integer key) {
        System.arraycopy(storage, key + 1, storage, key, size - 1 - key);
        storage[size - 1] = null;
        size--;
    }

    @Override
    public void insertResume(Resume resume) {
        checkStorageSize(resume);
        int position = -searchKey(resume.getUuid()) - 1;
        System.arraycopy(storage, position, storage, position + 1, size - position);
        storage[position] = resume;
        size++;
    }

    @Override
    protected Integer searchKey(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

}
