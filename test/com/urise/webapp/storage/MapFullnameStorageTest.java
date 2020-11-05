package com.urise.webapp.storage;

import com.urise.webapp.exeption.NotExistStorageException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MapFullnameStorageTest extends AbstractStorageTest {

    MapFullnameStorageTest() {
        super(new MapFullnameStorage());
    }

    @Test
    void get() {
        assertEquals(resume_1, storage.get("Vasilii Petrov"));
    }

    @Test
    void save() {
        int sizeBefore = storage.size();
        storage.save(resume_777);
        storage.save(resume_1);
        assertEquals(sizeBefore + 1, storage.size());
        assertEquals(resume_777, storage.get("Juan Lopez"));
    }

    @Test
    void update() {
        storage.update(resume_1);
        assertEquals(resume_1, storage.get("Vasilii Petrov"));
    }

    @Test
    void delete() {
        int sizeBefore = storage.size();
        storage.delete("Vasilii Petrov");
        assertEquals(sizeBefore - 1, storage.size());
        assertThrows(NotExistStorageException.class, () -> storage.get("Vasilii Petrov"));
    }
}