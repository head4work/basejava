/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    void clear() {
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] != null) {
                storage[i] = null;
            }
        }
    }

    void save(Resume r) {
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] == null) {
                storage[i] = r;
                break;
            }
        }
    }

    Resume get(String uuid) {
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] != null && storage[i].uuid.equals(uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
        int indexOfDel = 0;
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] != null && storage[i].uuid.equals(uuid)) {
                indexOfDel = i;
            }
        }
        for (int i = indexOfDel; i < storage.length - 1; i++) {
                storage[i] = storage[i + 1];
                storage[i + 1] = null;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        int count = 0;
        for (Resume r : storage) {
            if (r != null) {
                count++;
            }
        }
        Resume[] onlyresume = new Resume[count];
        for (int i = 0; i < onlyresume.length; i++) {
            onlyresume[i] = storage[i];
        }
        return onlyresume;
    }

    int size() {
        int count = 0;
        for (Resume r : storage) {
            if (r != null) {
                count++;
            }
        }
        return count;
    }
}
