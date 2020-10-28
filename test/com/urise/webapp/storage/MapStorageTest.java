package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;


public class MapStorageTest extends AbstractStorageTest {
    public MapStorageTest() {
        super(new MapStorage());
    }

    @Test
    void getAll() {
        Resume[] initialArray = {new Resume(UUID_1), new Resume(UUID_2), new Resume(UUID_3)};
        Resume[] storageMapArray = storage.getAll();
        Arrays.sort(storageMapArray);
        Assertions.assertArrayEquals(initialArray, storageMapArray);
    }
}
