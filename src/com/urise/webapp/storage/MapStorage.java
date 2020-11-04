package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapStorage extends AbstractStorage<String> {
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
    public void updateResume(Resume resume, Object key) {
        storage.replace((String) key, resume);
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

    public Resume[] getAll() {
        Collection<Resume> values = storage.values();
        Resume[] result = values.toArray(Resume[]::new);
        Arrays.sort(result, Comparator.comparing(Resume::getUuid));
        return result;
    }

    /*public List<Resume> getAllSorted() {
        List<Resume> resumes = new ArrayList<>(storage.values());
        resumes.sort(Comparator.comparing(Resume::getFullName));
        return resumes;
    }*/

    @Override
    protected List<Resume> getResumes() {
        return new ArrayList<>(storage.values());
    }

    public int size() {
        return storage.size();
    }

}
