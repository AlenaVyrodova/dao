package com.epam.rd.autocode;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface ResultSetMapper<T> {
    T map(ResultSet resultSet) throws SQLException;
}
