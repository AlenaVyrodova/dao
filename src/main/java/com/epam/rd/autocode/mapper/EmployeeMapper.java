package com.epam.rd.autocode.mapper;

import com.epam.rd.autocode.domain.Employee;
import com.epam.rd.autocode.domain.FullName;
import com.epam.rd.autocode.domain.Position;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class EmployeeMapper {
    public static Employee map(ResultSet resultSet) throws SQLException {
        BigInteger id = BigInteger.valueOf(resultSet.getInt("ID"));
        String firstName = resultSet.getString("FIRSTNAME");
        String lastName = resultSet.getString("LASTNAME");
        String middleName = resultSet.getString("MIDDLENAME");
        FullName fullName = new FullName(firstName, lastName, middleName);

        Position position = Position.valueOf(resultSet.getString("POSITION"));
        LocalDate hireDate = resultSet.getDate("HIREDATE").toLocalDate();
        BigDecimal salary = resultSet.getBigDecimal("SALARY");
        BigInteger managerId = BigInteger.valueOf(resultSet.getInt("MANAGER"));
        BigInteger departmentId = BigInteger.valueOf(resultSet.getInt("DEPARTMENT"));

        return new Employee(id, fullName, position, hireDate, salary, managerId, departmentId);
    }
}
