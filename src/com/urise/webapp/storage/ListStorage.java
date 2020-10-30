package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;

public class ListStorage extends AbstractStorage<Integer> {
    private final ArrayList<Resume> storage = new ArrayList<>();

    @Override
    protected boolean checkResumeExist(Resume resume) {
        return storage.contains(resume);
    }

    @Override
    protected Integer searchKey(String uuid) {
        return storage.indexOf(new Resume(uuid));
    }

    @Override
    protected void insertResume(Resume resume) {
        storage.add(resume);
    }

    @Override
    protected void removeResume(Integer key) {
        storage.remove((int) key);
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
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(Resume[]::new);
    }

    @Override
    public int size() {
        return storage.size();
    }

}
