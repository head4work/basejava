package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage<Resume> {
    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    protected void deleteResume(Resume resume) {
        storage.remove(resume.getUuid());
    }

    @Override
    protected void saveResume(Resume resume, Resume key) {
        storage.put(resume.getUuid(), resume);
    }

    public void clear() {
        storage.clear();
    }

    @Override
    public void updateResume(Resume resume, Resume key) {
        storage.replace(resume.getUuid(), resume);
    }

    @Override
    public Resume getResume(Resume resume) {
        return storage.get(resume.getUuid());
    }

    @Override
    protected boolean checkResumeExist(Resume resume) {
        return storage.containsKey(resume.getUuid());
    }

    @Override
    protected Resume searchKey(String uuid) {
        for (String key : storage.keySet()) {
            if (key.equals(uuid)) {
                return storage.get(key);
            }
        }
        return new Resume(uuid);
    }

    @Override
    protected List<Resume> getResumes() {
        return new ArrayList<>(storage.values());
    }

    public int size() {
        return storage.size();
    }

}
