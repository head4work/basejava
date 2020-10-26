package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class MapStorage extends AbstractStorage {
    protected static Map<String, Resume> storage = new HashMap<>();

    @Override
    protected void saveResume(Resume resume, int index) {
        insertResume(resume, index);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public void deleteResume(int index, String uuid) {
        removeResume(index, uuid);
    }

    @Override
    public void updateResume(Resume resume, int index) {
        storage.replace(resume.getUuid(),resume);
    }

    @Override
    public Resume getResume(int index, String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected boolean checkResumeExist(Resume resume) {
        return storage.containsValue(resume);
    }

    @Override
    protected abstract int getIndex(String uuid);

    @Override
    protected void insertResume(Resume resume, int index) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected void removeResume(int index, String uuid) {
        storage.remove(uuid);
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
