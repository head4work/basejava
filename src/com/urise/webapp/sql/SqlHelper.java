package com.urise.webapp.sql;

import com.urise.webapp.exeption.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.SqlStorage;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {

    public static Resume executeStatement(String statement, StatementSettings statementSettings) {
        try (PreparedStatement ps = SqlStorage.connectionFactory.getConnection().prepareStatement(statement)) {
            Resume r = statementSettings.execute(ps);
            ps.execute();
            return r;
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    public interface StatementSettings {
        Resume execute(PreparedStatement ps) throws SQLException;
    }
}
