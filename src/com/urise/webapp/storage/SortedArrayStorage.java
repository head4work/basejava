package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;


public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void removeResume(Object key) {
        System.arraycopy(storage, (Integer) key + 1, storage, (Integer) key, size - 1 - (Integer) key);
        storage[size - 1] = null;
        size--;
    }

    @Override
    public void insertResume(Resume resume) {
        checkStorageSize(resume);
        int position = -keySearch(resume.getUuid()) - 1;
        System.arraycopy(storage, position, storage, position + 1, size - position);
        storage[position] = resume;
        size++;
    }


    @Override
    protected Integer keySearch(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

}
