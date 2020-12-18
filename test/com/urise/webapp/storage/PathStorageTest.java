package com.urise.webapp.storage;

import com.urise.webapp.storage.serializer.ObjectStrategy;

class PathStorageTest extends AbstractStorageTest {

    PathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getPath(), new ObjectStrategy()));
    }
}