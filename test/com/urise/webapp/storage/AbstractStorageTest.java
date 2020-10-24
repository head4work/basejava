package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AbstractStorageTest {
    private Storage storage = new ListStorage();


    @Test
    void save() {
      storage.save(new Resume("uuid777"));
      Assertions.assertTrue(AbstractStorage.list.contains(new Resume("uuid777")));
    }

    @Test
    void delete() {
        storage.save(new Resume("uuid777"));
        Assertions.assertTrue(AbstractStorage.list.contains(new Resume("uuid777")));
        storage.delete("uuid777");
        Assertions.assertFalse(AbstractStorage.list.contains(new Resume("uid777")));
    }

    @Test
    void get() {
    }

    @Test
    void clear() {
    }

    @Test
    void getAll() {
    }

    @Test
    void update() {
    }

    @Test
    void size() {
    }
}