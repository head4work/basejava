package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;


public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void deleteMeta(int index) {
        System.arraycopy(storage, index + 1, storage, index, size - 1 - index);
    }

    @Override
    protected void saveMeta(Resume resume) {
        if (size == 0) {
            storage[0] = resume;
        } else {
            int position = Arrays.binarySearch(storage,
                    0, size, resume) * (-1) - 1;
            System.arraycopy(storage, position, storage, position + 1, size - position);
            storage[position] = resume;
        }
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

}
