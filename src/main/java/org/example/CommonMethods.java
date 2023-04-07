package org.example;

import java.sql.*;

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
    public void showBooksData() throws SQLException {
        String query = "select*from book;";
        Connection connection = this.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            System.out.println(rs.getInt(1) + " " + rs.getString(2));
        }
        statement.close();
        connection.close();
    }
}
