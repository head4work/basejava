package com.urise.webapp.storage;

import com.urise.webapp.util.Config;

public class SqlStorageTest extends AbstractStorageTest {

    SqlStorageTest() {
        super(Config.get().getStorage());
    }

}