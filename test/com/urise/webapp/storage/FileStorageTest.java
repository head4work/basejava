package com.urise.webapp.storage;

import com.urise.webapp.storage.serializer.ObjectStrategy;

class FileStorageTest extends AbstractStorageTest {

    FileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new ObjectStrategy()));
    }
}