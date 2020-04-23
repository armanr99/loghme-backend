package com.loghme.database.Mapper;

import com.loghme.database.ConncetionPool.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;

public abstract class Mapper<T, I> implements IMapper<T, I> {
    protected abstract String getFindStatement(I id);

    protected abstract String getInsertStatement(T t);

    protected abstract String getDeleteStatement(I id);

    protected abstract ArrayList<String> getCreateTableStatement();

    protected abstract T convertResultSetToObject(ResultSet rs) throws SQLException;

    public T find(I id) throws SQLException {
        try (Connection con = ConnectionPool.getInstance().getConnection();
                PreparedStatement st = con.prepareStatement(getFindStatement(id))) {
            ResultSet resultSet;
            try {
                resultSet = st.executeQuery();
                resultSet.next();
                return convertResultSetToObject(resultSet);
            } catch (SQLException ex) {
                System.out.println("error in Mapper.findByID query.");
                throw ex;
            }
        }
    }

    public void insert(T obj) throws SQLException {
        try (Connection con = ConnectionPool.getInstance().getConnection();
                PreparedStatement st = con.prepareStatement(getInsertStatement(obj))) {
            try {
                st.executeUpdate();
            } catch (SQLException ex) {
                System.out.println("error in Mapper.insert query.");
                throw ex;
            }
        }
    }

    public void delete(I id) throws SQLException {
        try (Connection con = ConnectionPool.getInstance().getConnection();
                PreparedStatement st = con.prepareStatement(getDeleteStatement(id))) {
            try {
                st.executeUpdate();
            } catch (SQLException ex) {
                System.out.println("error in Mapper.delete query.");
                throw ex;
            }
        }
    }

    protected void createTable() throws SQLException {
        try (Connection con = ConnectionPool.getInstance().getConnection();
                Statement stmt = con.createStatement(); ) {
            try {
                for (String stmtString : getCreateTableStatement()) {
                    stmt.addBatch(stmtString);
                }
                stmt.executeBatch();
                stmt.close();
                con.close();
            } catch (SQLException ex) {
                System.out.println("error in Mapper.createTable query.");
                throw ex;
            }
        }
    }
}
