package com.urise.webapp.storage;

import com.urise.webapp.exeption.ExistStorageException;
import com.urise.webapp.exeption.NotExistStorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.util.Config;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import static com.urise.webapp.util.ResumeTestDataSimple.createResumeWithSimpleData;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

abstract class AbstractStorageTest {
    protected final static File STORAGE_DIR = Config.get().getStorageDir();

    Storage storage;
    static final String UUID_1 = UUID.randomUUID().toString();
    static final String UUID_2 = UUID.randomUUID().toString();
    static final String UUID_3 = UUID.randomUUID().toString();
    static final String UUID_dummy = UUID.randomUUID().toString();
    static final String UUID_777 = UUID.randomUUID().toString();
    static Resume resume_1;
    static Resume resume_2;
    static Resume resume_3;
    static Resume resume_777;

    static {
        try {
            resume_1 = createResumeWithSimpleData(UUID_1, "Григорий Кислин");
            resume_2 = createResumeWithSimpleData(UUID_2, "Name2");
            resume_3 = createResumeWithSimpleData(UUID_3, "Name3");
            resume_777 = createResumeWithSimpleData(UUID_777, "Name777");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    void setUp() {
        storage.clear();
        storage.save(resume_1);
        storage.save(resume_2);
        storage.save(resume_3);
    }

    @Test
    void save() {
        int sizeBefore = storage.size();
        storage.save(resume_777);
        assertEquals(sizeBefore + 1, storage.size());
        assertEquals(resume_777, storage.get(UUID_777));
    }

    @Test
    void saveAlreadyExistUuid() {
        assertThrows(ExistStorageException.class, () -> storage.save(resume_1));
    }

    @Test
    void delete() {
        int sizeBefore = storage.size();
        storage.delete(UUID_1);
        assertEquals(sizeBefore - 1, storage.size());
        assertThrows(NotExistStorageException.class, () -> storage.get(UUID_1));
    }

    @Test
    void deleteNotExistUuid() {
        assertThrows(NotExistStorageException.class, () -> storage.delete(UUID_dummy));
    }

    @Test
    void get() {
        assertEquals(resume_1, storage.get(UUID_1));
    }

    @Test
    void getNotExistUuid() {
        assertThrows(NotExistStorageException.class, () -> storage.get(UUID_dummy));
    }

    @Test
    void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    void getAllSorted() {
        List<Resume> expectedResumes = Arrays.asList(resume_1, resume_2, resume_3);
        expectedResumes.sort(Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid));
        assertEquals(expectedResumes, storage.getAllSorted());
    }

    @Test
    void update() {
        storage.update(resume_1);
        assertEquals(resume_1, storage.get(UUID_1));
    }

    @Test
    void updateNotExistUuid() {
        assertThrows(NotExistStorageException.class, () -> storage.update(new Resume(UUID_dummy, "dummy")));
    }

    @Test
    void size() {
        assertEquals(3, storage.size());
    }
}