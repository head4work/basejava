package com.urise.webapp.storage;

import com.urise.webapp.storage.serializer.DataStrategy;

class DataPathStorageTest extends AbstractStorageTest {

    DataPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getPath(), new DataStrategy()));
    }
}