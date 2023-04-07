package org.example;

import com.google.gson.Gson;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class FileToDB {
    CommonMethods commonMethods = new CommonMethods();
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    public List<Contact> getDataFromCSVFile() throws IOException, CsvException {
        String url = "D:\\BridgeLabzz\\AddressBookJDBCNew\\src\\main\\resources\\Contact.csv";
        Path filePath = Path.of(url);
        Reader reader = Files.newBufferedReader(filePath);
        CSVReaderBuilder csvReaderBuilder = new CSVReaderBuilder(reader).withSkipLines(1);
        CSVReader csvReader = csvReaderBuilder.build();
        String[] stringList;
        List<Contact> contactList = new ArrayList<>();
        while ((stringList = csvReader.readNext()) != null) {
            contactList.add(new Contact(Integer.parseInt(stringList[0]), stringList[1], stringList[2], Long.parseLong(stringList[3]), stringList[4], stringList[5], stringList[6], stringList[7], Long.parseLong(stringList[8])));
        }
        return contactList;
    }

    private void writeListToDB(List<Contact> contactList) throws SQLException {
        String query = "insert into contact(id,firstName,lastName,phoneNumber,emailId,address,city,state,pinCode) " +
                "values(?,?,?,?,?,?,?,?,?)";
        for (Contact contact : contactList) {
            Connection connection = commonMethods.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, contact.getBookId());
            statement.setString(2, contact.getFirstName());
            statement.setString(3, contact.getLastName());
            statement.setLong(4, contact.getPhoneNumber());
            statement.setString(5, contact.geteMail());
            statement.setString(6, contact.getAddress());
            statement.setString(7, contact.getCity());
            statement.setString(8, contact.getState());
            statement.setLong(9, contact.getPinCode());
            statement.executeUpdate();
            statement.close();
            connection.close();
        }
    }

    private List<Contact> getDataFromJSONFile() throws IOException {
        String url = "D:\\BridgeLabzz\\AddressBookJDBCNew\\src\\main\\resources\\Contact.json";
        Path filePath = Paths.get(url);
        Reader reader = Files.newBufferedReader(filePath);
        Gson gson = new Gson();
        List<Contact> contactList = Arrays.asList(gson.fromJson(reader, Contact[].class));
        contactList.forEach(System.out::println);
        return contactList;
    }

    public void fileOp() throws IOException, SQLException, CsvException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("""
                enter option from which file you want to add contacts:-
                1.CSV to database
                2.JSON to database
                """);
        int choice = Integer.parseInt(bufferedReader.readLine());
        List<Contact> contactList;
        if (choice == 1)
            contactList = getDataFromCSVFile();
        else
            contactList = getDataFromJSONFile();
        writeListToDB(contactList);
    }

    private List<Contact> getCSVData() throws IOException, CsvValidationException {
        String url = "D:\\BridgeLabzz\\AddressBookJDBCNew\\src\\main\\resources\\Contact2.csv";
        Path filePath = Path.of(url);
        Reader reader = Files.newBufferedReader(filePath);
        CSVReaderBuilder csvReaderBuilder = new CSVReaderBuilder(reader).withSkipLines(1);
        CSVReader csvReader = csvReaderBuilder.build();
        String[] stringList;
        List<Contact> contactList = new ArrayList<>();
        while ((stringList = csvReader.readNext()) != null) {
            contactList.add(new Contact(stringList[0], stringList[1], Long.parseLong(stringList[2]), stringList[3], stringList[4], stringList[5], stringList[6], Long.parseLong(stringList[7])));
        }
        return contactList;
    }

    public List<Contact> getJSONData() throws IOException {
        String url = "D:\\BridgeLabzz\\AddressBookJDBCNew\\src\\main\\resources\\Contact2.json";
        Path filePath = Paths.get(url);
        Reader reader = Files.newBufferedReader(filePath);
        Gson gson = new Gson();
        List<Contact> contactList = Arrays.asList(gson.fromJson(reader, Contact[].class));
        return contactList;
    }

    private void writeParticularContactFromFileTODB(List<Contact> contactList, int bookId) throws SQLException {
        String query = "insert into contact(id,firstName,lastName,phoneNumber,emailId,address,city,state,pinCode) " +
                "values(?,?,?,?,?,?,?,?,?)";
        for (Contact contact : contactList) {
            Connection connection = commonMethods.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, bookId);
            statement.setString(2, contact.getFirstName());
            statement.setString(3, contact.getLastName());
            statement.setLong(4, contact.getPhoneNumber());
            statement.setString(5, contact.geteMail());
            statement.setString(6, contact.getAddress());
            statement.setString(7, contact.getCity());
            statement.setString(8, contact.getState());
            statement.setLong(9, contact.getPinCode());
            statement.executeUpdate();
            statement.close();
            connection.close();
        }
    }

    public void fileToDbIntoParticularBook() throws SQLException, IOException, CsvValidationException {
        boolean loop = true;
        List<Contact> contactList;
        while (loop) {
            System.out.println("Enter choice from which file you want to read data:-\n(1)CSV\n(2)JSON");
            int option = Integer.parseInt(bufferedReader.readLine());
            if (option == 1) contactList = getCSVData();
            else contactList = getJSONData();
            commonMethods.showBooksData();
            System.out.println("Enter the book id from above into which you want to enter contact");
            int bookId = Integer.parseInt(bufferedReader.readLine());
            for (Contact contact : contactList) {
                System.out.println(contact.getFirstName() + " " + contact.getLastName());
            }
            System.out.println("Enter the contact first name you want to enter ");
            String firstName = bufferedReader.readLine();
            System.out.println("Enter the contact last name you want to enter ");
            String lastName = bufferedReader.readLine();
            List<Contact> personList = new ArrayList<>();
            for (Contact contact : contactList) {
                if (contact.getFirstName().equalsIgnoreCase(firstName) && contact.getLastName().equalsIgnoreCase(lastName))
                    personList.add(new Contact(contact.getFirstName(), contact.getLastName(), contact.getPhoneNumber(),
                            contact.geteMail(), contact.getAddress(), contact.getCity(), contact.getState(), contact.getPinCode()));
            }
            writeParticularContactFromFileTODB(personList, bookId);
            System.out.println("if you want to add more contacts type add:-");
            String choice = bufferedReader.readLine();
            if (!(choice.equalsIgnoreCase("add"))) loop = false;
        }
    }


}
