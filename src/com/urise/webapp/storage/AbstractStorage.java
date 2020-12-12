package com.urise.webapp.storage;

import com.urise.webapp.exeption.ExistStorageException;
import com.urise.webapp.exeption.NotExistStorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.strategy.SerializeStrategy;

import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<T> implements Storage {
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());
    protected SerializeStrategy serializeStrategy;

    public void save(Resume resume) {
        LOG.info("save " + resume);
        T key = receiveKeyIfResumeNotExist(resume);
        saveResume(resume, key);
    }

    public void delete(String uuid) {
        LOG.info("delete " + uuid);
        T key = receiveKeyIfResumeExist(uuid);
        deleteResume(key);
    }

    public Resume get(String uuid) {
        LOG.info("get " + uuid);
        T key = receiveKeyIfResumeExist(uuid);
        return getResume(key);
    }

    public void update(Resume resume) {
        LOG.info("update " + resume);
        T key = receiveKeyIfResumeExist(resume.getUuid());
        updateResume(resume, key);
    }

    private T receiveKeyIfResumeExist(String uuid) {
        T key = searchKey(uuid);
        if (!checkResumeExist(key)) {
            LOG.warning("Resume with uuid (" + uuid + ") doesn't exist.");
            throw new NotExistStorageException(uuid);
        }
        return key;
    }

    private T receiveKeyIfResumeNotExist(Resume resume) {
        T key = searchKey(resume.getUuid());
        if (checkResumeExist(key)) {
            LOG.warning("Resume with uuid (" + resume.getUuid() + ") already exist.");
            throw new ExistStorageException(resume.getUuid());
        }
        return key;
    }

    public List<Resume> getAllSorted() {
        List<Resume> resumes = getResumes();
        resumes.sort(Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid));
        return resumes;
    }

    protected abstract List<Resume> getResumes();

    protected abstract void deleteResume(T key);

    protected abstract void saveResume(Resume resume, T key);

    public abstract void updateResume(Resume resume, T key);

    public abstract Resume getResume(T key);

    protected abstract boolean checkResumeExist(T key);

    protected abstract T searchKey(String uuid);

}
