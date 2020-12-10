package com.urise.webapp.storage;

class PathStorageTest extends AbstractStorageTest {

    PathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getPath()));
    }
}