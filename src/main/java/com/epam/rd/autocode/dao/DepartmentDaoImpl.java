package com.epam.rd.autocode.dao;

import com.epam.rd.autocode.DatabaseHandler;
import com.epam.rd.autocode.domain.Department;
import com.epam.rd.autocode.mapper.DepartmentMapper;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;


public class DepartmentDaoImpl implements DepartmentDao {
    private final DatabaseHandler dbHandler = new DatabaseHandler();
    private final String GET_BY_ID = "SELECT * FROM department WHERE id=?";
    private final String GET_ALL = "SELECT * FROM department";
    private final String INSERT = "INSERT INTO department (NAME, LOCATION, ID) VALUES (?, ?, ?)";
    private final String UPDATE = "UPDATE department SET NAME=?, LOCATION=? WHERE id=?";
    private final String DELETE = "DELETE FROM department WHERE id=?";

    @Override
    public Optional<Department> getById(BigInteger id) {
        return dbHandler.executeQueryForObject(GET_BY_ID,
                statement -> statement.setInt(1, id.intValue()),
                DepartmentMapper::map);
    }

    @Override
    public List<Department> getAll() {
        return dbHandler.executeQueryForList(GET_ALL,
                statement -> {},
                DepartmentMapper::map);
    }

    @Override
    public Department save(Department department) {
        if (getById(department.getId()).isPresent()) {
            dbHandler.executeUpdate(UPDATE, statement -> {
                statement.setString(1, department.getName());
                statement.setString(2, department.getLocation());
                statement.setInt(3, department.getId().intValue());
            });
        } else {
            dbHandler.executeUpdate(INSERT, statement -> {
                statement.setString(1, department.getName());
                statement.setString(2, department.getLocation());
                statement.setInt(3, department.getId().intValue());
            });
        }
        return department;
    }

    @Override
    public void delete(Department department) {
        dbHandler.executeUpdate(DELETE,
                statement -> statement.setInt(1, department.getId().intValue()));
    }
}