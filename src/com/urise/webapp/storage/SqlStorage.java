package com.urise.webapp.storage;

import com.urise.webapp.exeption.NotExistStorageException;
import com.urise.webapp.exeption.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.ConnectionFactory;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SqlStorage implements Storage {
    public static ConnectionFactory connectionFactory;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void update(Resume resume) {
        try {
            get(resume.getUuid());
            executeStatement("UPDATE  resume SET full_name = ? WHERE uuid =?", ps -> {
                ps.setString(1, resume.getFullName());
                ps.setString(2, resume.getUuid());
                return null;
            });
        } catch (Exception e) {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    @Override
    public void clear() {
        executeStatement("DELETE FROM resume", ps -> null);
    }

    @Override
    public void save(Resume resume) {
        executeStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)", ps -> {
            ps.setString(1, resume.getUuid());
            ps.setString(2, resume.getFullName());
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return executeStatement("SELECT * FROM resume r WHERE r.uuid =?", ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, rs.getString("full_name"));
        });
    }

    @Override
    public void delete(String uuid) {
        try {
            get(uuid);
            executeStatement("DELETE  FROM resume  WHERE uuid =?", ps -> {
                ps.setString(1, uuid);
                return null;
            });
        } catch (Exception e) {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> list = new ArrayList<>();
        executeStatement("SELECT * FROM resume ", ps -> {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Resume(rs.getString("uuid").trim(), rs.getString("full_name").trim()));
            }
            return null;
        });
        list.sort(Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid));
        return list;

    }

    @Override
    public int size() {
        return getAllSorted().size();
    }

    private Resume executeStatement(String statement, BlockOfCode blockOfCode) {
        try (PreparedStatement ps = connectionFactory.getConnection().prepareStatement(statement)) {
            Resume r = blockOfCode.execute(ps);
            ps.execute();
            return r;
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    private interface BlockOfCode {
        Resume execute(PreparedStatement ps) throws SQLException;
    }

}
