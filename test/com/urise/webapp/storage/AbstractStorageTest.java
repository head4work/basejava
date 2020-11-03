package com.urise.webapp.storage;

import com.urise.webapp.exeption.ExistStorageException;
import com.urise.webapp.exeption.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

abstract class AbstractStorageTest {
    Storage storage;
    static final String UUID_1 = "uuid1";
    static final String UUID_2 = "uuid2";
    static final String UUID_3 = "uuid3";
    static final String UUID_dummy = "dummy";
    static final String UUID_777 = "uuid777";
    static Resume resume_1 = new Resume(UUID_1);
    static Resume resume_2 = new Resume(UUID_2);
    static Resume resume_3 = new Resume(UUID_3);
    static Resume resume_777 = new Resume(UUID_777);

    AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    void setUp() {
        storage.clear();
        storage.save(resume_1);
        storage.save(resume_2);
        storage.save(resume_3);
    }

    @Test
    void save() {
        int sizeBefore = storage.size();
        storage.save(resume_777);
        assertEquals(sizeBefore + 1, storage.size());
        assertEquals(resume_777, storage.get(UUID_777));
    }

    @Test
    void saveAlreadyExistUuid() {
        assertThrows(ExistStorageException.class, () -> storage.save(resume_1));
    }

    @Test
    void delete() {
        int sizeBefore = storage.size();
        storage.delete(UUID_1);
        assertEquals(sizeBefore - 1, storage.size());
        assertThrows(NotExistStorageException.class, () -> storage.get(UUID_1));
    }

    @Test
    void deleteNotExistUuid() {
        assertThrows(NotExistStorageException.class, () -> storage.delete(UUID_dummy));
    }

    @Test
    void get() {
        assertEquals(resume_1, storage.get(UUID_1));
    }

    @Test
    void getNotExistUuid() {
        assertThrows(NotExistStorageException.class, () -> storage.get(UUID_dummy));
    }

    @Test
    void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    void getAll() {
        Resume[] expectedResumes = {resume_1, resume_2, resume_3};
        assertArrayEquals(expectedResumes, storage.getAll());
    }

    @Test
    void update() {
        storage.update(resume_1);
        assertEquals(resume_1, storage.get(UUID_1));
    }

    @Test
    void updateNotExistUuid() {
        assertThrows(NotExistStorageException.class, () -> storage.update(new Resume(UUID_dummy)));
    }

    @Test
    void size() {
        assertEquals(3, storage.size());
    }
}