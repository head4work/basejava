package com.urise.webapp.storage;

import com.urise.webapp.exeption.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.serializer.SerializeStrategy;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
    private final Path directory;
    private final SerializeStrategy serializeStrategy;

    protected PathStorage(String path, SerializeStrategy serializeStrategy) {
        directory = Paths.get(path);
        this.serializeStrategy = serializeStrategy;
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(path + " is not a directory or is not writable");
        }
    }

    @Override
    protected void deleteResume(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("File delete error", null);
        }
    }

    @Override
    protected void saveResume(Resume resume, Path path) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("Couldn't create file ", null);
        }
        updateResume(resume, path);
    }

    @Override
    public void updateResume(Resume resume, Path path) {
        try {
            serializeStrategy.writeResume(resume, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (Exception e) {
            throw new StorageException("File write error", resume.getUuid(), e);
        }
    }

    @Override
    public Resume getResume(Path path) {
        try {
            return serializeStrategy.readResume(new BufferedInputStream(Files.newInputStream(path)));
        } catch (Exception e) {
            throw new StorageException("File read error ", path.toFile().getName(), e);
        }
    }

    @Override
    protected boolean checkResumeExist(Path path) {
        return Files.exists(path);
    }

    @Override
    protected Path searchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected List<Resume> getResumes() {
        return pathStream().map(this::getResume).collect(Collectors.toList());
    }

    @Override
    public void clear() {
        pathStream().forEach(this::deleteResume);
    }

    @Override
    public int size() {
        return (int) pathStream().count();
    }

    private Stream<Path> pathStream() {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("Directory read error", null);
        }
    }

}
