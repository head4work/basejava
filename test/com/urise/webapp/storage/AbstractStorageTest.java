package com.urise.webapp.storage;

import com.urise.webapp.exeption.ExistStorageException;
import com.urise.webapp.exeption.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

abstract class AbstractStorageTest {
    protected Storage storage;
    protected static final String UUID_1 = "uuid1";
    protected static final String UUID_2 = "uuid2";
    protected static final String UUID_3 = "uuid3";
    protected static final String UUID_dummy = "dummy";
    protected static final String UUID_777 = "uuid777";
    protected static Resume Resume_1 = new Resume(UUID_1);
    protected static Resume Resume_2 = new Resume(UUID_2);
    protected static Resume Resume_3 = new Resume(UUID_3);
    protected static Resume Resume_777 = new Resume(UUID_777);

    AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    void setUp() {
        storage.clear();
        storage.save(Resume_1);
        storage.save(Resume_2);
        storage.save(Resume_3);
    }

    @Test
    void save() {
        int sizeBefore = storage.size();
        storage.save(Resume_777);
        assertEquals(sizeBefore + 1, storage.size());
        assertEquals(Resume_777, storage.get(UUID_777));
    }

    @Test
    void saveAlreadyExistUuid() {
        assertThrows(ExistStorageException.class, () -> storage.save(Resume_1));
    }

    @Test
    void delete() {
        int sizeBefore = storage.size();
        storage.delete(UUID_1);
        assertEquals(sizeBefore - 1, storage.size());
    }

    @Test
    void deleteNotExistUuid() {
        assertThrows(NotExistStorageException.class, () -> storage.delete(UUID_dummy));
    }

    @Test
    void get() {
        assertEquals(storage.get(UUID_1), Resume_1);
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
        Resume[] expectedResumes = {Resume_1, Resume_2, Resume_3};
        assertArrayEquals(expectedResumes, storage.getAll());
    }

    @Test
    void update() {
        storage.save(Resume_777);
        storage.update(Resume_777);
        assertEquals(storage.get(UUID_777), Resume_777);
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