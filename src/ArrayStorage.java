/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    public int size = 0;
    public int optimized = optSize();

    private int optSize() {
        if (size != 0) {
            return size;
        }
        return storage.length;
    }

    void clear() {
        size = 0;
        for (int i = 0; i < optimized; i++) {
            if (storage[i] != null) {
                storage[i] = null;
            }
        }
    }

    void save(Resume r) {
        for (int i = 0; i < optimized; i++) {
            if (storage[i] == null) {
                storage[i] = r;
                size++;
                break;
            }
        }
    }

    Resume get(String uuid) {
        for (int i = 0; i < optimized; i++) {
            if (storage[i] != null && storage[i].uuid.equals(uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
        int indexOfDel = 0;
        for (int i = 0; i < optimized; i++) {
            if (storage[i] != null && storage[i].uuid.equals(uuid)) {
                indexOfDel = i;
                size--;
            }
        }
        for (int i = indexOfDel; i < optimized - 1; i++) {
            storage[i] = storage[i + 1];
            storage[i + 1] = null;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] resumes = new Resume[size];
        for (int i = 0; i < resumes.length; i++) {
            resumes[i] = storage[i];
        }
        return resumes;
    }

    int size() {
        return size;
    }
}
