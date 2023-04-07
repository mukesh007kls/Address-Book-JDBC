package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataRetrieving {

    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    private Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/addressBook";
        String userName = "root";
        String password = "klsa2921";
        return DriverManager.getConnection(url, userName, password);
    }

    public List<Contact> readDataFromDb() throws SQLException {
        String query = "select*from contact;";
        List<Contact> contacts = new ArrayList<>();
        Contact contact = new Contact();
        Connection connection = this.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            contact.setBookId(rs.getInt(1));
            contact.setFirstName(rs.getString(2));
            contact.setLastName(rs.getString(3));
            contact.setPhoneNumber(rs.getLong(4));
            contact.seteMail(rs.getString(5));
            contact.setAddress(rs.getString(6));
            contact.setCity(rs.getString(7));
            contact.setState(rs.getString(8));
            contact.setPinCode(rs.getLong(9));
            contacts.add(new Contact(contact.getBookId(), contact.getFirstName(), contact.getLastName(), contact.getPhoneNumber(),
                    contact.geteMail(), contact.getAddress(), contact.getCity(), contact.getState(), contact.getPinCode()));
        }
        return contacts;
    }

    public List<Contact> getContactByName(String firstName, String lastName) throws SQLException {
        String query = String.format("select*from contact where firstName='%s' and lastName='%s';", firstName, lastName);
        List<Contact> contacts = new ArrayList<>();
        Contact contact = new Contact();
        Connection connection = this.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            contact.setBookId(rs.getInt(1));
            contact.setFirstName(rs.getString(2));
            contact.setLastName(rs.getString(3));
            contact.setPhoneNumber(rs.getLong(4));
            contact.seteMail(rs.getString(5));
            contact.setAddress(rs.getString(6));
            contact.setCity(rs.getString(7));
            contact.setState(rs.getString(8));
            contact.setPinCode(rs.getLong(9));
            contacts.add(new Contact(contact.getBookId(), contact.getFirstName(), contact.getLastName(), contact.getPhoneNumber(),
                    contact.geteMail(), contact.getAddress(), contact.getCity(), contact.getState(), contact.getPinCode()));
        }
        return contacts;
    }

}
