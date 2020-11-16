package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapUuidStorage extends AbstractStorage<String> {
    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    protected void deleteResume(String key) {
        storage.remove(key);
    }

    @Override
    protected void saveResume(Resume resume, String key) {
        storage.put(resume.getUuid(), resume);
    }

    public void clear() {
        storage.clear();
    }

    @Override
    public void updateResume(Resume resume, String key) {
        storage.replace(key, resume);
    }

    @Override
    public Resume getResume(String key) {
        return storage.get(key);
    }

    @Override
    protected boolean checkResumeExist(String key) {
        return storage.containsKey(key);
    }

    @Override
    protected String searchKey(String uuid) {
        return uuid;
    }

    @Override
    protected List<Resume> getResumes() {
        return new ArrayList<>(storage.values());
    }

    public int size() {
        return storage.size();
    }

}
