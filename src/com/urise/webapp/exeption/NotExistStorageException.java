package com.urise.webapp.exeption;

public class NotExistStorageException extends StorageException {
    public NotExistStorageException(String uuid) {
        super("Resume with uuid (" + uuid + ") doesn't exist.",uuid);
    }
}
