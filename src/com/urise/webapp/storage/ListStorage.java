package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;

public class ListStorage extends AbstractStorage {
    protected static ArrayList<Resume> storage = new ArrayList<>();

    @Override
    protected boolean checkResumeExist(Resume resume) {
        return storage.contains(resume);
    }

    @Override
    public void deleteResume(int index) {
        removeResume(index);
    }

    @Override
    public void saveResume(Resume resume, int index) {
        insertResume(resume, index);
    }

    @Override
    protected int getIndex(String uuid) {
        return storage.indexOf(new Resume(uuid));
    }

    @Override
    protected void insertResume(Resume resume, int index) {
        storage.add(resume);
    }

    @Override
    protected void removeResume(int index) {
        storage.remove(index);
    }

    @Override
    public Resume getResume(int index) {
        return storage.get(index);
    }

    @Override
    public void updateResume(Resume resume, int index) {
        storage.set(index, resume);
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
