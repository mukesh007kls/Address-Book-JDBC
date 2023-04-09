package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookCRUD {
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    CommonMethods commonMethods = new CommonMethods();

    public void createBook() throws SQLException, IOException {
        ContactCRUD crud = new ContactCRUD();
        List<String> bookList = new ArrayList<>();
        System.out.println("Enter book name:-");
        String bookName = bufferedReader.readLine();
        String query = "select (bookName) from book;";
        Connection connection = commonMethods.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            bookList.add(rs.getString(1));
        }
        statement.close();
        connection.close();
        if (bookList.contains(bookName)) {
            System.out.println("Book exists");
            return;
        }
        insertIntoBookTable(bookName);
        System.out.println("if you want to add data into the book then type add:-");
        String choice = bufferedReader.readLine();
        if (choice.equalsIgnoreCase("add")) crud.contactCRUD();
    }

    public void createBookTable() throws SQLException {
        String query = "create table book(" +
                "bookId int primary key auto_increment," +
                "bookName varchar(100)" +
                ");";
        commonMethods.executeConnection(query);
    }

    public void dropBookTable() throws SQLException {
        String query = "drop table book;";
        commonMethods.executeConnection(query);
    }

    public void insertIntoBookTable(String bookName) throws SQLException {
        String query = String.format("insert into book(bookName) value('%s');", bookName);
        System.out.println(bookName + " added to table");
        commonMethods.executeConnection(query);
    }


    public void deleteBookData() throws SQLException, IOException {
        List<Integer> bookList = commonMethods.showBooksData();
        if (bookList.isEmpty()) {
            System.out.println("No books found");
        } else {
            System.out.println("enter the book id you want to delete from the above list:-");
            int bookId = Integer.parseInt(bufferedReader.readLine());
            String query1 = String.format("delete from contact where id=%s", bookId);
            commonMethods.executeConnection(query1);
            String query2 = String.format("delete from book where bookId=%s", bookId);
            commonMethods.executeConnection(query2);
        }
    }

    public void insertIntoBookTable(int bookId) throws SQLException, IOException {
        System.out.println("Enter book name:-");
        String bookName = bufferedReader.readLine();
        String query = String.format("insert into book(bookId,bookName) value(%s,'%s');", bookId, bookName);
        System.out.println(bookName + " added to table");
        commonMethods.executeConnection(query);
    }

    public void insertIntoBook() throws SQLException, IOException {
        System.out.println("Enter book name:-");
        String bookName = bufferedReader.readLine();
        String query = String.format("insert into book(bookName) value('%s');", bookName);
        System.out.println(bookName + " added to table");
        commonMethods.executeConnection(query);
    }
}
