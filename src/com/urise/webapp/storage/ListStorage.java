package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {
    private final List<Resume> storage = new ArrayList<>();

    /*public List<Resume> getAllSorted() {
        List<Resume> resumes = new ArrayList<>(storage);
       resumes.sort(Comparator.comparing(Resume::getFullName));
        return resumes;
    }*/

    @Override
    protected List<Resume> getResumes() {
        return new ArrayList<>(storage);
    }

    @Override
    protected boolean checkResumeExist(Integer key) {
        return key >= 0;
    }

    @Override
    protected Integer searchKey(String uuid) {
        for (Resume r : storage) {
            if (r.getUuid().equals(uuid)) {
                return storage.indexOf(r);
            }
        }
        return -1;
    }

    @Override
    public void updateResume(Resume resume, Integer key) {
        storage.set(key, resume);
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
