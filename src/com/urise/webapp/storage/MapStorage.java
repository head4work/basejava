package com.urise.webapp.storage;

import com.urise.webapp.exeption.ExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage<String> {
    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    public void save(Resume resume) {
        if (!checkResumeExist(resume)) {
            insertResume(resume);
        } else {
            throw new ExistStorageException(resume.getUuid());
        }
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
    protected boolean checkResumeExist(Resume resume) {
        return storage.containsValue(resume);
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
        return values.toArray(Resume[]::new);
    }

    @Override
    public int size() {
        return storage.size();
    }

    protected void insertResume(Resume resume) {
        storage.put(resume.getUuid(), resume);
    }


}
