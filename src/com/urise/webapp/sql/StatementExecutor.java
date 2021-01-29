package com.urise.webapp.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface StatementExecutor<T> {
    T execute(PreparedStatement ps) throws SQLException;
}