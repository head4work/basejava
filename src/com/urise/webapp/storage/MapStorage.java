package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapStorage extends AbstractStorage<String> {
    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    protected void deleteResume(String key) {
        removeResume(key);
    }

    @Override
    protected void saveResume(Resume resume, String key) {
        insertResume(resume, key);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public void updateResume(Resume resume, Object key) {
        storage.replace((String) key, resume);
    }

    @Override
    public Resume getResume(String key) {
        return storage.get(key);
    }

    @Override
    protected boolean checkResumeExist(String key) {
        return storage.containsValue(new Resume(key));
    }

    @Override
    protected String searchKey(String uuid) {
        return uuid;
    }

    @Override
    protected void removeResume(String key) {
        storage.remove(key);
    }

    @Override
    public Resume[] getAll() {
        Collection<Resume> values = storage.values();
        Resume[] result = values.toArray(Resume[]::new);
        Arrays.sort(result, Comparator.comparing(Resume::getUuid));
        return result;
    }

    @Override
    public int size() {
        return storage.size();
    }

    protected void insertResume(Resume resume, String key) {
        storage.put(resume.getUuid(), resume);
    }


}
