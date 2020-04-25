package com.loghme.database.Mapper;

import com.loghme.database.ConncetionPool.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;

public abstract class Mapper<T, I> implements IMapper<T, I> {
    protected abstract ArrayList<String> getCreateTableStatement();

    protected abstract T convertResultSetToObject(ResultSet rs) throws SQLException;

    private void closeStatement(Connection con, Statement st) throws SQLException {
        st.close();
        con.close();
    }

    protected T findOne(Connection con, PreparedStatement st) throws SQLException {
        ResultSet resultSet;
        try {
            resultSet = st.executeQuery();
            if (!resultSet.next()) {
                resultSet.close();
                closeStatement(con, st);
                return null;
            }
            T convertedObject = convertResultSetToObject(resultSet);
            resultSet.close();
            closeStatement(con, st);
            return convertedObject;
        } catch (SQLException ex) {
            System.out.println("error in Mapper.findByID query.");
            throw ex;
        }
    }

    protected ArrayList<T> findAll(Connection con, PreparedStatement st) throws SQLException {
        ArrayList<T> result = new ArrayList<>();
        ResultSet resultSet;
        try {
            resultSet = st.executeQuery();
            while (resultSet.next()) {
                result.add(convertResultSetToObject(resultSet));
            }
            resultSet.close();
            closeStatement(con, st);
            return result;
        } catch (SQLException ex) {
            System.out.println("error in Mapper.findByID query.");
            throw ex;
        }
    }

    protected void executeUpdate(Connection con, PreparedStatement st) throws SQLException {
        try {
            st.executeUpdate();
            closeStatement(con, st);
        } catch (SQLException ex) {
            System.out.println("error in Mapper.insert query.");
            throw ex;
        }
    }

    protected void executeUpdateBatch(Connection con, PreparedStatement st) throws SQLException {
        try {
            st.executeBatch();
            closeStatement(con, st);
        } catch (SQLException ex) {
            System.out.println("error in Mapper.insert query.");
            throw ex;
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
            } catch (SQLException ex) {
                System.out.println("error in Mapper.createTable query.");
                throw ex;
            }
        }
    }
}
