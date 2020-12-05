package com.urise.webapp.storage;

class ObjectStreamFileStorageTest extends AbstractStorageTest {

    ObjectStreamFileStorageTest() {
        super(new ObjectStreamFileStorage(STORAGE_DIR));
    }

}

