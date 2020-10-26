package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class MapStorageTest extends AbstractStorageTest {
    public MapStorageTest() {
        super(new MapStorage());
    }

    @Test
    void getAll() {
        Collection<Resume> values = MapStorage.storage.values();
        Resume[] check = values.toArray(Resume[]::new);
        assertArrayEquals(check, storage.getAll());
    }
}
