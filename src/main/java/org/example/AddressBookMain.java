package org.example;

import com.opencsv.exceptions.CsvException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

import static org.example.Constants.*;

public class AddressBookMain {
    public static void main(String[] args) {
        CommonMethods commonMethods = new CommonMethods();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BookCRUD bookCRUD = new BookCRUD();
        ContactCRUD crud = new ContactCRUD();
        FileToDB fileToDB = new FileToDB();
        System.out.println("Welcome to Address Book");
        try {
            bookCRUD.createBookTable();
            crud.createContactTable();
            boolean loop = true;
            while (loop) {
                System.out.println("""
                        1.Add new address book
                        2.Add contact details
                        3.show book
                        4.delete book
                        5.print all contact
                        6.write file data into db
                        7.Add particular contact to db from particular file
                        0.exit
                        """);
                int choice = Integer.parseInt(bufferedReader.readLine());
                switch (choice) {
                    case ADD_BOOK -> bookCRUD.createBook();
                    case ADD_CONTACT -> crud.contactCRUD();
                    case DISPLAY_BOOK -> commonMethods.showBooksData();
                    case DELETE_BOOK -> bookCRUD.deleteBookData();
                    case PRINT_ALL_CONTACTS -> crud.printAllContacts();
                    case WRITE_FILE_DATA_INTO_DB -> fileToDB.fileOp();
                    case ADD_PARTICULAR_CONTACT_TO_DB -> fileToDB.fileToDbIntoParticularBook();
                    case EXIT -> {
                        crud.dropContactTable();
                        bookCRUD.dropBookTable();
                        loop = false;
                    }
                }
            }

        } catch (IOException | CsvException | SQLException e) {
            e.printStackTrace();
        }
    }
}