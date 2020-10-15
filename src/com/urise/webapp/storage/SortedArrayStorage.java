package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;


public class SortedArrayStorage extends AbstractArrayStorage {

    //  public Resume[] sorted = Arrays.copyOf(storage,STORAGE_LIMIT);


    @Override
    protected void saveMeta(Resume resume) {
        if (size == 0) {
            storage[0] = resume;
            size++;
        } else {
            int position = Arrays.binarySearch(storage,
                    0, size, resume) * (-1) - 1;
            System.arraycopy(storage, position, storage, position + 1, size - position);
            storage[position] = resume;
            size++;
        }
    }

    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            System.arraycopy(storage, index + 1, storage, index, size - 1 - index);
            size--;
        } else {
            System.out.println("ERROR: Resume with uuid (" + uuid + ") doesn't exist.");
        }
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

}
