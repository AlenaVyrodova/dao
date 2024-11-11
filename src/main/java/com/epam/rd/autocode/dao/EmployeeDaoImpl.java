package com.epam.rd.autocode.dao;

import com.epam.rd.autocode.ConnectionSource;
import com.epam.rd.autocode.DatabaseHandler;
import com.epam.rd.autocode.domain.Department;
import com.epam.rd.autocode.domain.Employee;
import com.epam.rd.autocode.domain.FullName;
import com.epam.rd.autocode.domain.Position;
import com.epam.rd.autocode.mapper.EmployeeMapper;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmployeeDaoImpl implements EmployeeDao{

    private final DatabaseHandler dbHandler = new DatabaseHandler();
    private final String GET_BY_ID = "SELECT * FROM Employee WHERE ID=?";
    private final String GET_ALL = "SELECT * FROM Employee";
    private final String INSERT = "INSERT INTO Employee (id, firstname, lastname," +
            " middlename, position, manager, hiredate, salary, department) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final String DELETE = "DELETE FROM Employee WHERE ID=?";
    private final String GET_BY_DEPARTMENT = "SELECT * FROM Employee WHERE DEPARTMENT=?";
    private final String GET_BY_MANAGER = "SELECT * FROM Employee WHERE MANAGER=?";
    @Override
    public Optional<Employee> getById(BigInteger Id) {
        return dbHandler.executeQueryForObject(GET_BY_ID,
                statement -> statement.setInt(1, Id.intValue()),
                EmployeeMapper::map);
    }

    @Override
    public List<Employee> getAll() {
        return dbHandler.executeQueryForList(GET_ALL,
                statement -> {},
                EmployeeMapper::map);
    }

    @Override
    public Employee save(Employee employee) {
        dbHandler.executeUpdate(INSERT, statement -> {
            statement.setInt(1, employee.getId().intValue());
            statement.setString(2, employee.getFullName().getFirstName());
            statement.setString(3, employee.getFullName().getLastName());
            statement.setString(4, employee.getFullName().getMiddleName());
            statement.setString(5, employee.getPosition().toString());
            statement.setInt(6, employee.getManagerId().intValue());
            statement.setDate(7, Date.valueOf(employee.getHired()));
            statement.setDouble(8, employee.getSalary().doubleValue());
            statement.setInt(9, employee.getDepartmentId().intValue());
        });
        return employee;
    }

    @Override
    public void delete(Employee employee) {
        dbHandler.executeUpdate(DELETE,
                statement -> statement.setInt(1, employee.getId().intValue()));
    }

    @Override
    public List<Employee> getByDepartment(Department department) {
        return dbHandler.executeQueryForList(GET_BY_DEPARTMENT,
                statement -> statement.setInt(1, department.getId().intValue()),
                EmployeeMapper::map);
    }

    @Override
    public List<Employee> getByManager(Employee employee) {
        return dbHandler.executeQueryForList(GET_BY_MANAGER,
                statement -> statement.setInt(1, employee.getId().intValue()),
                EmployeeMapper::map);
    }
}