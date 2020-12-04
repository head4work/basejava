package com.urise.webapp.storage;

import org.junit.jupiter.api.Test;

class ObjectStreamStorageTest extends AbstractStorageTest {

    ObjectStreamStorageTest() {
        super(new ObjectStreamStorage(STORAGE_DIR));
    }

    @Test
    void writeResume() {
    }

    @Test
    void readResume() {
    }
}