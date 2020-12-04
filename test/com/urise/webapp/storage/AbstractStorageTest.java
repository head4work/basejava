package com.urise.webapp.storage;

import com.urise.webapp.exeption.ExistStorageException;
import com.urise.webapp.exeption.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static com.urise.webapp.util.ResumeTestData.createResumeWithData;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

abstract class AbstractStorageTest {
    protected final static File STORAGE_DIR = new File("D:\\Basejava\\basejava\\storage");

    Storage storage;
    static final String UUID_1 = "uuid1";
    static final String UUID_2 = "uuid2";
    static final String UUID_3 = "uuid3";
    static final String UUID_dummy = "dummy";
    static final String UUID_777 = "uuid777";
    static Resume resume_1;
    static Resume resume_2;
    static Resume resume_3;
    static Resume resume_777;

    static {
        try {
            resume_1 = createResumeWithData(UUID_1, "Григорий Кислин");
            resume_2 = createResumeWithData(UUID_2, "Name2");
            resume_3 = createResumeWithData(UUID_3, "Name3");
            resume_777 = createResumeWithData(UUID_777, "Name777");
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