package com.urise.webapp.storage;

import com.urise.webapp.exeption.NotExistStorageException;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SqlStorage implements Storage {
    SqlHelper helper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        helper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void update(Resume resume) {
        helper.transactionalExecute(conn -> {
            String uuid = resume.getUuid();
            try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid =?")) {
                ps.setString(1, resume.getFullName());
                ps.setString(2, uuid);
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(uuid);
                }
            }
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM contact  WHERE resume_uuid = ?  ")) {
                ps.setString(1, uuid);
                ps.execute();
            }
            insertContacts(resume, conn);
            return null;
        });
    }


    @Override
    public void clear() {
        helper.execute("DELETE FROM resume", ps -> {
            ps.execute();
            return null;
        });
    }

    @Override
    public void save(Resume resume) {
        helper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, resume.getFullName());
                ps.execute();
            }
            insertContacts(resume, conn);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return helper.execute("" +
                "SELECT * FROM resume r " +
                "    LEFT JOIN contact c" +
                "           ON r.uuid = c.resume_uuid " +
                "        WHERE r.uuid =?", ps -> {
            ps.setString(1, uuid);
            ps.execute();
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            Resume r = new Resume(uuid, rs.getString("full_name"));
            do {
                addContacts(rs, r);
            } while (rs.next());
            return r;
        });
    }

    @Override
    public void delete(String uuid) {
        helper.execute("DELETE  FROM resume  WHERE uuid =?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        Map<String, Resume> map = new HashMap<>();
        helper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume ORDER BY full_name, uuid")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("uuid");
                    Resume r = new Resume(uuid, rs.getString("full_name"));
                    map.put(uuid, r);
                }
            }
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM contact ")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    addContacts(rs, map.get(rs.getString("resume_uuid")));
                }
            }
            return null;
        });
        return map.values().stream().sorted(Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid)).collect(Collectors.toList());
    }

    @Override
    public int size() {
        return helper.execute("SELECT COUNT(uuid) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        });
    }

    private void addContacts(ResultSet rs, Resume r) throws SQLException {
        String value = rs.getString("value");
        String type = rs.getString("type");
        if (value != null) {
            r.addContact(ContactType.valueOf(type), value);
        }
    }

    private void insertContacts(Resume resume, Connection conn) throws SQLException {
        if (resume.getContacts().size() > 0) {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type,value) VALUES (?,?,?)")) {
                for (Map.Entry<ContactType, String> e : resume.getContacts().entrySet()) {
                    ps.setString(1, resume.getUuid());
                    ps.setString(2, e.getKey().name());
                    ps.setString(3, e.getValue());
                    ps.addBatch();
                }
                ps.executeBatch();
            }
        }
    }
}
