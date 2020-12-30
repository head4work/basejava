package com.urise.webapp.storage.serializer;

import java.io.IOException;

public interface MyBiConsumer<T, U> {
    void accept(T t, U u) throws IOException;
}
