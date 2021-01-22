package com.urise.webapp.storage;

import com.urise.webapp.exeption.StorageException;
import com.urise.webapp.util.Config;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class SqlStorageTest extends AbstractStorageTest {

    SqlStorageTest() {
        super(new SqlStorage(Config.get().getDbUrl(), Config.get().getDbUser(), Config.get().getDbPassword()));
    }

    @Test
    void saveAlreadyExistUuid() {
        assertThrows(StorageException.class, () -> storage.save(resume_1));
    }
}