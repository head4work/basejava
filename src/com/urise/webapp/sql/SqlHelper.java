package com.urise.webapp.sql;

import com.urise.webapp.exeption.ExistStorageException;
import com.urise.webapp.exeption.StorageException;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public <T> T executeStatement(String statement, StatementExecutor<T> statementExecutor) {
        try (PreparedStatement ps = connectionFactory.getConnection().prepareStatement(statement)) {
            return statementExecutor.execute(ps);
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                throw new ExistStorageException(e.getMessage());
            } else {
                throw new StorageException(e);
            }
        }
    }

    public interface StatementExecutor<T> {
        T execute(PreparedStatement ps) throws SQLException;
    }
}
