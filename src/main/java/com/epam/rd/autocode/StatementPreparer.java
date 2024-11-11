package com.epam.rd.autocode;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface StatementPreparer {
    void prepare(PreparedStatement statement) throws SQLException;
}

