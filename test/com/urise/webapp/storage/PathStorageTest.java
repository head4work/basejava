package com.urise.webapp.storage;

import com.urise.webapp.storage.strategy.ObjectStrategy;

class PathStorageTest extends AbstractStorageTest {

    PathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getPath(), new ObjectStrategy()));
    }
}