package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

abstract class AbstractStorageTest {
    private Storage storage;

    AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    void setUp() {
        storage.clear();
        storage.save(new Resume("uuid777"));
    }

    @Test
    void save() {
        Assertions.assertTrue(AbstractStorage.list.contains(new Resume("uuid777")));
    }

    @Test
    void delete() {
        storage.delete("uuid777");
        Assertions.assertFalse(AbstractStorage.list.contains(new Resume("uid777")));
    }

    @Test
    void get() {
        assertEquals(storage.get("uuid777"), new Resume("uuid777"));
    }

    @Test
    void clear() {
        storage.clear();
        assertEquals(0,storage.size());
    }

    @Test
    void getAll() {
        assertArrayEquals(AbstractStorage.list.toArray(),storage.getAll());
    }

    @Test
    void update() {
        storage.update(storage.get("uuid777"));
        assertEquals(storage.get("uuid777"), new Resume("uuid777"));
    }

    @Test
    void size() {
        assertEquals(1,storage.size());
    }
}