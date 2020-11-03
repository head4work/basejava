package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {
    private final List<Resume> storage = new ArrayList<>();

    @Override
    protected boolean checkResumeExist(Integer key) {
        return key >= 0;
    }

    @Override
    protected Integer searchKey(String uuid) {
        return storage.indexOf(new Resume(uuid));
    }

    @Override
    public void updateResume(Resume resume, Object key) {
        storage.set((int) key, resume);
    }

    @Override
    public Resume getResume(Integer key) {
        return storage.get(key);
    }

    @Override
    protected void deleteResume(Integer key) {
        storage.remove((int) key);
    }

    @Override
    protected void saveResume(Resume resume, Integer key) {
        storage.add(resume);
    }

    public void clear() {
        storage.clear();
    }


    public Resume[] getAll() {
        return storage.toArray(Resume[]::new);
    }

    public int size() {
        return storage.size();
    }

}
