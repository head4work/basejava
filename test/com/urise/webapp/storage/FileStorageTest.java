package com.urise.webapp.storage;

import com.urise.webapp.storage.strategy.ObjectStrategy;

class FileStorageTest extends AbstractStorageTest {

    FileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new ObjectStrategy()));
    }
}