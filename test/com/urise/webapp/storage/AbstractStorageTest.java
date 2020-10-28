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

    AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

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
    void deleteNotExistUuid() {
        assertThrows(NotExistStorageException.class, () -> storage.delete(UUID_dummy));
    }

    /* @Test
     void delete() {
         storage.delete("uuid777");
         Assertions.assertFalse(ListStorage.storage.contains(new Resume("uid777")));
     }*/
    @Test
    void get() {
        assertEquals(storage.get(UUID_1), new Resume(UUID_1));
    }

    @Test
    void getNotExistUuid() {
        assertThrows(NotExistStorageException.class, () -> storage.get(UUID_dummy));
    }

    /*@Test
    void get() {
        assertEquals(storage.get("uuid777"), new Resume("uuid777"));
    }
*/
    @Test
    void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    void getAll() {
        Resume[] check = {new Resume(UUID_1), new Resume(UUID_2), new Resume(UUID_3)};
        assertArrayEquals(check, storage.getAll());
    }

    @Test
    void update() {
        storage.update(storage.get("uuid1"));
        assertEquals(storage.get("uuid1"), new Resume("uuid1"));
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