package com.codecool.blazpie;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DatabaseAccess {

    private Connection connection;

    public DatabaseAccess (String databaseFilePath) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:file:src/main/resources/gb.db");
    }


    public List<List<String>> getEntries() throws SQLException {
        String getStatement = "Select author, message, timestamp from entries ORDER BY id desc;";
        PreparedStatement stmt = connection.prepareStatement(getStatement);
        List<List<String>> result = null;
        if (stmt.execute()) {
            ResultSet rs = stmt.getResultSet();
            result = parseResultSet(rs);
        }
        return result;
    }

    public boolean addRecord (Map<String, String> record) throws SQLException {
        String updateStatement = "insert into entries(author, message, timestamp) values (?, ?, ?);";
        PreparedStatement stmt = connection.prepareStatement(updateStatement);
        System.out.println(record);
        stmt.setString(1, record.get("author"));
        stmt.setString(2, record.get("message"));
        stmt.setString(3, record.get("timestamp"));
        return stmt.executeUpdate() == 1;
    }

    private List<List<String>> parseResultSet(ResultSet rs) throws SQLException {
        List<List<String>> result = new ArrayList<>();
        while (rs.next()) {
            List<String> line = new ArrayList<>();
            line.add(rs.getString(1));
            line.add(rs.getString(2));
            line.add(rs.getString(3));
            result.add(line);
        }
        return result;
    }

}
