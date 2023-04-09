package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommonMethods {
    public Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/addressBook";
        String userName = "root";
        String password = "klsa2921";
        return DriverManager.getConnection(url, userName, password);
    }

    public void executeConnection(String query) throws SQLException {
        Connection connection = this.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.executeUpdate();
        statement.close();
        connection.close();
    }
    public List<Integer> showBooksData() throws SQLException {
        String query = "select*from book;";
        List<Integer> bookIdList = new ArrayList<>();
        Connection connection = this.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            bookIdList.add(rs.getInt(1));
            System.out.println(rs.getInt(1) + " " + rs.getString(2));
        }
        statement.close();
        connection.close();
        return bookIdList;
    }
}
