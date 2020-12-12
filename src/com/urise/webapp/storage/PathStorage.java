package com.urise.webapp.storage;

import com.urise.webapp.exeption.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.strategy.ObjectStrategy;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PathStorage extends AbstractStorage<Path> {
    private final Path directory;

    protected PathStorage(String dir) {
        serializeStrategy = new ObjectStrategy();
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not a directory or is not writable");
        }
    }

    @Override
    protected List<Resume> getResumes() {
        List<Resume> list;
        try {
            list = Files.list(directory).map(this::getResume).collect(Collectors.toList());
        } catch (IOException e) {
            throw new StorageException("Directory read error", null);
        }
        return list;
    }

    @Override
    protected void deleteResume(Path dir) {
        try {
            Files.delete(dir);
        } catch (IOException e) {
            throw new StorageException("File delete error", null);
        }
    }

    @Override
    protected void saveResume(Resume resume, Path dir) {
        try {
            Files.createFile(dir);
        } catch (IOException e) {
            throw new StorageException("Couldn't create file ", null);
        }
        updateResume(resume, dir);
    }

    @Override
    public void updateResume(Resume resume, Path dir) {
        try {
            serializeStrategy.writeResume(resume, new BufferedOutputStream(new FileOutputStream(dir.toFile())));
        } catch (Exception e) {
            throw new StorageException("File write error", resume.getUuid(), e);
        }
    }

    @Override
    public Resume getResume(Path dir) {
        try {
            return serializeStrategy.readResume(new BufferedInputStream(new FileInputStream(dir.toFile())));
        } catch (Exception e) {
            throw new StorageException("File read error ", dir.toFile().getName(), e);
        }
    }

    @Override
    protected boolean checkResumeExist(Path dir) {
        return Files.exists(dir);
    }

    @Override
    protected Path searchKey(String uuid) {
        return Path.of(directory.toString(), uuid);
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::deleteResume);
        } catch (IOException e) {
            throw new StorageException("Directory read error", null);
        }
    }

    @Override
    public int size() {
        try {
            return (int) Files.list(directory).count();
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new StorageException("Directory read error", null);
    }


}
