package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public class ListStorage extends AbstractStorage {
    @Override
    protected boolean checkResumeExist(Resume resume) {
        return list.contains(resume);
    }

    @Override
    public void deleteResume(int index) {
        ejectResume(index);
    }

    @Override
    public void saveResume(Resume resume, int index) {
        injectResume(resume, index);
    }

    @Override
    protected int getIndex(String uuid) {
        return list.indexOf(new Resume(uuid));
    }

    @Override
    protected void injectResume(Resume resume, int index) {
        list.add(resume);
    }

    @Override
    protected void ejectResume(int index) {
        list.remove(index);
    }

    @Override
    public void updateResume(Resume resume, int index) {
        list.set(index, resume);
    }
}
