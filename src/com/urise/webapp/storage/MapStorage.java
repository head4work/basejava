package com.urise.webapp.storage;

import com.urise.webapp.exeption.ExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    private Map<String, Resume> storage = new HashMap<>();

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
    public void updateResume(Resume resume, int index) {
        storage.replace(resume.getUuid(), resume);
    }

    @Override
    public Resume getResume(Object key) {
        return storage.get(key);
    }

    @Override
    protected boolean checkResumeExist(Resume resume) {
        return storage.containsValue(resume);
    }

    @Override
    protected String keySearch(String uuid) {
        return uuid;
    }


    protected void insertResume(Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected void removeResume(Object key) {
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

}
