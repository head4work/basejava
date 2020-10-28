package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;

public class ListStorage extends AbstractStorage {
    private  ArrayList<Resume> storage = new ArrayList<>();

    @Override
    protected boolean checkResumeExist(Resume resume) {
        return storage.contains(resume);
    }




    @Override
    protected Integer keySearch(String uuid) {
        return storage.indexOf(new Resume(uuid));
    }

    @Override
    protected void insertResume(Resume resume) {
        storage.add(resume);
    }

    @Override
    protected void removeResume(Object key) {
        int i = (Integer) key;
        storage.remove(i);
    }





    @Override
    public void updateResume(Resume resume, int index) {
        storage.set(index, resume);
    }

    @Override
    public Resume getResume(Object key) {
        return storage.get((Integer) key);
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
