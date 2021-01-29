package com.urise.webapp.storage;

import com.urise.webapp.exeption.NotExistStorageException;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {
    SqlHelper helper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        helper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void update(Resume resume) {
        helper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE  resume SET full_name = ? WHERE uuid =?")) {
                ps.setString(1, resume.getFullName());
                ps.setString(2, resume.getUuid());
                if (ps.executeUpdate() != 1) {
                    throw new NotExistStorageException(resume.getUuid());
                }
            }
            return insertContacts(resume, conn);
        });

        helper.executeStatement("UPDATE  resume SET full_name = ? WHERE uuid =?", ps -> {
            ps.setString(1, resume.getFullName());
            ps.setString(2, resume.getUuid());
            if (ps.executeUpdate() != 1) {
                throw new NotExistStorageException(resume.getUuid());
            }
            return null;
        });

    }


    @Override
    public void clear() {
        helper.executeStatement("DELETE FROM resume", ps -> {
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
            return insertContacts(resume, conn);
        });
      /*  helper.executeStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)", ps -> {
            ps.setString(1, resume.getUuid());
            ps.setString(2, resume.getFullName());
            return null;
        });
        for (Map.Entry<ContactType, String> e : resume.getContacts().entrySet()) {
            helper.executeStatement("INSERT INTO contact (resume_uuid, type,value) VALUES (?,?,?)", ps -> {
                ps.setString(1, resume.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3,e.getValue());
                ps.execute();
                return null;
            });
        }*/
    }

    @Override
    public Resume get(String uuid) {
        return helper.executeStatement("SELECT * FROM resume r " +
                "LEFT JOIN contact c" +
                " ON r.uuid = c.resume_uuid " +
                " WHERE r.uuid =?", ps -> {
            ps.setString(1, uuid);
            ps.execute();
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            Resume r = new Resume(uuid, rs.getString("full_name"));
            do {
                String value = rs.getString("value");
                String type = rs.getString("type");
                r.addContact(ContactType.valueOf(type), value);
            } while (rs.next());
            return r;
        });
    }

    @Override
    public void delete(String uuid) {
        helper.executeStatement("DELETE  FROM resume  WHERE uuid =?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() != 1) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> list = new ArrayList<>();
        helper.executeStatement("SELECT * FROM resume ORDER BY full_name, uuid", ps -> {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
            }
            return null;
        });
        return list;
    }

    @Override
    public int size() {
        return helper.executeStatement("SELECT COUNT(uuid) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        });

    }

    private <T> T insertContacts(Resume resume, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type,value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> e : resume.getContacts().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
        return null;
    }
}
