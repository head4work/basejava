package com.urise.webapp.storage;

class FileStorageTest extends AbstractStorageTest {

    FileStorageTest() {
        super(new FileStorage(STORAGE_DIR));
    }
}