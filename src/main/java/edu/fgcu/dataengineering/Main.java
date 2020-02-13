package edu.fgcu.dataengineering;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws IOException, CsvValidationException, SQLException {
        // Literally just calls our parser right now (....and is called for tests)
        CsvParser csvP = new CsvParser("src/Data/bookstore_report2.csv");
        csvP.printCsv();
        Reader read = Files.newBufferedReader(Paths.get("src/Data/bookstore_report2.csv"));
        CSVReader reader = new CSVReader(read);

        String[] bookInfo;

        while ((bookInfo = reader.readNext()) != null) {
            String sqlInsert = "INSERT INTO book(isbn, book_title, author_name, publisher_name) " +
                    "VALUES(?,?,?,?)";
            try {
                PreparedStatement pstatement = BookStoreDB.getDb().getConn().prepareStatement(sqlInsert);
                pstatement.setString(1, bookInfo[0]);
                pstatement.setString(2,bookInfo[1]);
                pstatement.setString(3,bookInfo[2]);
                pstatement.setString(4,bookInfo[3]);
                pstatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Load the json
        /*
        1. Create instance of GSON
        2. Create a JsonReader object using FileReader
        3. Array of class instances of AuthorParser, assign data from our JsonReader
        4. foreach loop to check data
         */
        Gson gson = new Gson();
        JsonReader jread = new JsonReader(new FileReader("src/Data/authors.json"));
        AuthorParser[] authors = gson.fromJson(jread, AuthorParser[].class);

        
        try {
            String name = "hello";
            String email = "bye";
            String url  = "please";

            String sqlQuery = "INSERT INTO author(author_name, author_email, author_url) VALUES (?, ?, ?)";
            PreparedStatement pstatement = BookStoreDB.getDb().getConn().prepareStatement(sqlQuery);


            for ( var element : authors) {
                pstatement.setString(1,element.getName());
                pstatement.setString(2,element.getEmail());
                pstatement.setString(3,element.getUrl());
                pstatement.executeUpdate();
            }

            System.out.println("hello");
        } catch (SQLException e) {
            e.printStackTrace();
        }


        for (var element : authors) {
            System.out.println(element.getName());
            System.out.println(element.getEmail());
            System.out.println(element.getUrl());
        }





    }


}