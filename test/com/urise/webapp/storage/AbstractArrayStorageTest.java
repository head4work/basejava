package com.urise.webapp.storage;

import com.urise.webapp.exeption.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {

    AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test
    void saveToOverflow() {
        for (int i = 0; i < AbstractArrayStorage.STORAGE_LIMIT - 3; i++) {
            storage.save(new Resume());
        }
        assertThrows(StorageException.class, () -> storage.save(new Resume()));
    }

    @Test
    void getAll() {
        assertTrue(Arrays.equals(Arrays.copyOf(AbstractArrayStorage.storage, storage.size()),
                storage.getAll()));
    }

}