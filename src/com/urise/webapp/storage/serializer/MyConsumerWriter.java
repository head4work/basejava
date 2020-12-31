package com.urise.webapp.storage.serializer;

import java.io.IOException;

public interface MyConsumerWriter<T> {
    void accept(T t) throws IOException;
}
