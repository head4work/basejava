package com.urise.webapp.storage;

import com.urise.webapp.exeption.ExistStorageException;
import com.urise.webapp.exeption.NotExistStorageException;
import com.urise.webapp.exeption.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public abstract class AbstractArrayStorageTest {
    AbstractArrayStorageTest(Storage storage) {
        AbstractArrayStorageTest.storage = storage;
    }

    static Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_dummy = "dummy";

    @BeforeEach
    void setUp() {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    void save() {
        int sizeBefore = storage.size();
        storage.save(new Resume("uuid777"));
        assertEquals(sizeBefore + 1, storage.size());
        assertEquals(new Resume("uuid777"), (storage.get("uuid777")));
    }

    @Test
    void saveToOverflow() {
        for (int i = 0; i < AbstractArrayStorage.STORAGE_LIMIT - 3; i++) {
            storage.save(new Resume());
        }
        assertThrows(StorageException.class, () -> storage.save(new Resume()));
    }

    @Test
    void saveAlreadyExistUuid() {
        assertThrows(ExistStorageException.class, () -> storage.save(new Resume(UUID_1)));
    }

    @Test
    void delete() {
        int sizeBefore = storage.size();
        storage.delete(UUID_1);
        assertEquals(sizeBefore - 1, storage.size());
    }

    @Test
    void deleteNotExisted() {
        assertThrows(NotExistStorageException.class, () -> storage.delete(UUID_dummy));
    }

    @Test
    void get() {
        assertEquals(storage.get(UUID_1), new Resume(UUID_1));
    }

    @Test
    void getNotExisted() {
        assertThrows(NotExistStorageException.class, () -> storage.get(UUID_dummy));
    }

    @Test
    void clear() {
        int sizeBefore = storage.size();
        storage.clear();
        assertEquals(sizeBefore - 3, storage.size());
    }

    @Test
    void getAll() {
        assertEquals(storage.size(), storage.getAll().length);
    }

    @Test
    void update() {
        Resume before = new Resume(UUID_1);
        storage.update(before);
        assertEquals(before, storage.get(UUID_1));
    }

    @Test
    void updateNotExisted() {
        assertThrows(NotExistStorageException.class, () -> storage.update(new Resume(UUID_dummy)));
    }

    @Test
    void size() {
        assertEquals(3, storage.size());
    }
}