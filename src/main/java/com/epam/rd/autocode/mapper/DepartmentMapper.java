package com.epam.rd.autocode.mapper;

import com.epam.rd.autocode.domain.Department;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DepartmentMapper {
    public static Department map(ResultSet resultSet) throws SQLException {
        BigInteger id = BigInteger.valueOf(resultSet.getInt("ID"));
        String name = resultSet.getString("NAME");
        String location = resultSet.getString("LOCATION");

        return new Department(id, name, location);
    }
}
