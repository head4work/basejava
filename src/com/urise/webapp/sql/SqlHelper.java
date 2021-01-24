package com.urise.webapp.sql;

import com.urise.webapp.exeption.ExistStorageException;
import com.urise.webapp.exeption.StorageException;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper<T> {
    public ConnectionFactory connectionFactory;

    public T executeStatement(String statement, StatementSettings<T> statementSettings) {
        try (PreparedStatement ps = connectionFactory.getConnection().prepareStatement(statement)) {
            return statementSettings.execute(ps);
        } catch (SQLException e) {
            System.out.println(e.getSQLState());
            if (e.getSQLState().equals("23505")) {
                throw new ExistStorageException(e.getMessage());
            } else {
                throw new StorageException(e);
            }
        }
    }

    public interface StatementSettings<T> {
        T execute(PreparedStatement ps) throws SQLException;
    }
}
