package org.example;

import static org.example.Constants.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;

public class ContactCRUD {
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    CommonMethods commonMethods = new CommonMethods();

    public void createContactTable() throws SQLException {
        String query = "create table contact(" +
                "id int," +
                "firstName varchar(150) not null," +
                "lastName varchar(150)," +
                "phoneNumber long," +
                "emailId varchar(150)," +
                "address varchar(250)," +
                "city varchar(100)," +
                "state varchar(100) default 'telangana'," +
                "pinCode long," +
                "foreign key(id) references book(bookId)" +
                ");";
        commonMethods.executeConnection(query);
    }


    public void dropContactTable() throws SQLException {
        String query = "drop table contact";
        commonMethods.executeConnection(query);
    }

    private void insertData(int bookId) throws SQLException, IOException {
        Contact contact = new Contact();
        contact.setBookId(bookId);
        String query = "insert into contact(id,firstName,lastName,phoneNumber,emailId,address,city,state,pinCode) " +
                "values(?,?,?,?,?,?,?,?,?)";
        Connection connection = commonMethods.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        System.out.println("Enter first name:-");
        contact.setFirstName(bufferedReader.readLine());

        System.out.println("Enter last name:-");
        contact.setLastName(bufferedReader.readLine());

        System.out.println("Enter phone number:-");
        contact.setPhoneNumber(Long.parseLong(bufferedReader.readLine()));

        System.out.println("Enter emailId:-");
        contact.seteMail(bufferedReader.readLine());

        System.out.println("Enter address:-");
        contact.setAddress(bufferedReader.readLine());

        System.out.println("Enter city:-");
        contact.setCity(bufferedReader.readLine());

        System.out.println("Enter state:-");
        contact.setState(bufferedReader.readLine());

        System.out.println("Enter pinCode:-");
        contact.setPinCode(Long.parseLong(bufferedReader.readLine()));

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

    public void printAllContacts() throws SQLException {
        String query = "select b.bookId,b.bookName,c.firstName,c.lastname,c.phoneNumber,c.emailId,c.address,c.city,c.state,c.pinCode " +
                "from book b " +
                "join contact c on b.bookId=c.id;";
        Connection connection = commonMethods.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            System.out.println(rs.getInt("bookId") + " " + rs.getString("bookName") + " " + rs.getString("firstName") + " " + rs.getString("lastName")
                    + " " + rs.getLong("phoneNumber") + " " + rs.getString("emailID") + " " + rs.getString("address")
                    + " " + rs.getString("city") + " " + rs.getString("state") + " " + rs.getLong("pinCode"));
        }
        statement.close();
        connection.close();
    }

    private void printContactOfParticularBook(int bookId) throws SQLException {
        String query = String.format("select b.bookName,c.firstName,c.lastname,c.phoneNumber,c.emailId,c.address,c.city,c.state,c.pinCode " +
                "from book b " +
                "join contact c on b.bookId=c.id where c.id=%s;", bookId);
        Connection connection = commonMethods.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            System.out.println(rs.getString("bookName") + " " + rs.getString("firstName") + " " + rs.getString("lastName")
                    + " " + rs.getLong("phoneNumber") + " " + rs.getString("emailID") + " " + rs.getString("address")
                    + " " + rs.getString("city") + " " + rs.getString("state") + " " + rs.getLong("pinCode"));
        }
        statement.close();
        connection.close();
    }

    private void updateDB(int bookId) throws IOException, SQLException {
        System.out.println("Enter first name:-");
        String firstName = bufferedReader.readLine();
        System.out.println("Enter Last name:-");
        String lastName = bufferedReader.readLine();
        boolean loop = true;
        while (loop) {
            System.out.println("Choose which data you want to update:-");
            System.out.println("1.First name\n2.Last Name\n3.Phone Number\n4.EMail\n5.Address\n6.City\n7.State\n8.PinCode\n0.exit");
            int choice = Integer.parseInt(bufferedReader.readLine());
            switch (choice) {
                case FIRST_NAME -> updateContact("firstName", bookId, firstName, lastName);
                case LAST_NAME -> updateContact("lastName", bookId, firstName, lastName);
                case PHONE_NUMBER -> updateContact("phoneNumber", bookId, firstName, lastName);
                case EMAIL_ID -> updateContact("emailID", bookId, firstName, lastName);
                case ADDRESS -> updateContact("address", bookId, firstName, lastName);
                case CITY -> updateContact("city", bookId, firstName, lastName);
                case STATE -> updateContact("state", bookId, firstName, lastName);
                case PIN_CODE -> updateContact("pinCode", bookId, firstName, lastName);
                case EXIT -> loop = false;
            }
        }
    }

    private void updateContact(String updateField, int bookId, String firstName, String lastName) throws IOException, SQLException {
        System.out.println("Enter the New " + updateField + " data:-");
        String newFirstName = bufferedReader.readLine();
        String query = String.format("update contact set %s='%s' where id=%s and firstName='%s' and lastName='%s';", updateField, newFirstName, bookId, firstName, lastName);
        commonMethods.executeConnection(query);
    }

    private void deleteContact(int bookId) throws SQLException, IOException {
        System.out.println("Enter first name of the contact you want to delete:-");
        String firstName = bufferedReader.readLine();
        System.out.println("Enter first name of the contact you want to delete:-");
        String lastName = bufferedReader.readLine();
        String query = String.format("delete from contact where id=%s firstName='%s' and lastName='%s';", bookId, firstName, lastName);
        commonMethods.executeConnection(query);
    }

    public void contactCRUD() throws IOException, SQLException {
        boolean loop = true;
        commonMethods.showBooksData();
        System.out.println("enter book id from above book details");
        String bookIdString = bufferedReader.readLine();
        int bookId = Integer.parseInt(bookIdString);
        while (loop) {
            System.out.println("Enter the choice:-\n(1)Add a new contact\n(2)Edit Contact Details\n(3)Display the Contacts\n(4)Delete a contact\n('0')To exit");
            int choice = Integer.parseInt(bufferedReader.readLine());
            switch (choice) {
                case ADD_NEW_CONTACT -> insertData(bookId);
                case EDIT_CONTACT -> updateDB(bookId);
                case DISPLAY_CONTACT -> printContactOfParticularBook(bookId);
                case DELETE_CONTACT -> deleteContact(bookId);
                case EXIT -> loop = false;
            }
        }
    }

}
