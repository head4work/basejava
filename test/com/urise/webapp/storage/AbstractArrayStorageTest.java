package com.urise.webapp.storage;

import com.urise.webapp.exeption.NotExistStorageException;
import com.urise.webapp.exeption.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AbstractArrayStorageTest {
    private static final Storage storage = new ArrayStorage();
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    @BeforeAll
    static void setUp() {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    void save() {
        int amountBefore = storage.size();
        storage.save(new Resume());
        assertEquals(amountBefore + 1, storage.size());
    }

    @Test
    void saveToOverflow() {
        for (int i = 0; i < 7; i++) {
            storage.save(new Resume());
        }
        assertThrows(StorageException.class, () -> storage.save(new Resume()));
    }

    @Test
    void delete() {
        int amountBefore = storage.size();
        storage.delete("uuid1");
        assertEquals(amountBefore - 1, storage.size());
    }

    @Test
    void get() {
        assertEquals(storage.get("uuid1"), new Resume(UUID_1));
    }

    @Test
    void getNotExisted() {
        assertThrows(NotExistStorageException.class, () -> storage.get("dummy"));
    }

    @Test
    void clear() {
        int amountBefore = storage.size();
        storage.clear();
        assertEquals(amountBefore - 3, storage.size());
    }

    @Test
    void getAll() {
        assertEquals(storage.size(), storage.getAll().length);
    }

    @Test
    void update() {
        Resume before = new Resume("uuid1");
        storage.update(before);
        assertEquals(storage.get("uuid1"), before);
    }

    @Test
    void size() {
        assertEquals(3, storage.size());
    }
}