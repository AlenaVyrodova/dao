package com.epam.rd.autocode;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DatabaseHandler {
    private final ConnectionSource connectionSource = ConnectionSource.instance();

    public <T> Optional<T> executeQueryForObject(String query, StatementPreparer preparer,
                                                 ResultSetMapper<T> mapper) {
        try (Connection connection = connectionSource.createConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            preparer.prepare(statement);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapper.map(resultSet));
                }
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return Optional.empty();
    }

    public <T> List<T> executeQueryForList(String query, StatementPreparer preparer, ResultSetMapper<T> mapper) {
        List<T> resultList = new ArrayList<>();
        try (Connection connection = connectionSource.createConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            preparer.prepare(statement);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    resultList.add(mapper.map(resultSet));
                }
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return resultList;
    }

    public void executeUpdate(String query, StatementPreparer preparer) {
        try (Connection connection = connectionSource.createConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            preparer.prepare(statement);
            statement.executeUpdate();
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }
    private void handleSQLException(SQLException e) {
        e.printStackTrace();
        throw new RuntimeException("Database operation failed", e);
    }
}