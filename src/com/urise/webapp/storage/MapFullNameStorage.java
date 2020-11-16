package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapFullNameStorage extends AbstractStorage<String> {
    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    protected void deleteResume(String fullName) {
        storage.remove(fullName);
    }

    @Override
    protected void saveResume(Resume resume, String fullName) {
        storage.put(fullName, resume);
    }

    public void clear() {
        storage.clear();
    }

    @Override
    public void updateResume(Resume resume, String fullName) {
        storage.replace(fullName, resume);
    }

    @Override
    public Resume getResume(String fullName) {
        return storage.get(fullName);
    }

    @Override
    protected boolean checkResumeExist(String fullName) {
        return storage.containsKey(fullName);
    }

    @Override
    protected String searchKey(String fullName) {
        return fullName;
    }

    @Override
    protected List<Resume> getResumes() {
        return new ArrayList<>(storage.values());
    }

    public int size() {
        return storage.size();
    }

}
