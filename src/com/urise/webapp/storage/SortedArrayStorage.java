package com.urise.webapp.storage;

import com.sun.source.tree.ArrayAccessTree;
import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;


public class SortedArrayStorage extends AbstractArrayStorage {

    //  public Resume[] sorted = Arrays.copyOf(storage,STORAGE_LIMIT);


    @Override
    public void update(Resume resume) {

    }

    @Override
    public void save(Resume resume) {
        if (getIndex(resume.getUuid()) < 0) {
            if (size < STORAGE_LIMIT) {
                if (size == 0) {
                    storage[0] = resume;
                    size++;
                } else {
                    int position = Arrays.binarySearch(storage,
                            0, size, resume) * -1 - 1;
                    for (int i = size; i > position; i--) {
                        storage[i] = storage[i - 1];
                    }
                    storage[position] = resume;
                    size++;
                }
            } else {
                System.out.println("ERROR: Storage is full.");
            }
        } else {
            System.out.println("ERROR: Resume with uuid (" + resume.getUuid() + ") already exist.");
        }
    }

    @Override
    public void delete(String uuid) {

    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

}
