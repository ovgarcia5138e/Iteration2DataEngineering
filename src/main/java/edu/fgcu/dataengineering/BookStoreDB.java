package edu.fgcu.dataengineering;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
// just to check if these are found

public class BookStoreDB {

    private static Database db;

    /**
     * This method returns a data base when called if it is not already created.
     *
     * @return A data base.
     */
    public static Database getDb() {
        if (db == null) {
            db = new Database();
        }
        return db;
    }

    /**
     * This method method will establish a connection the the created H2 data base.
     */
    public static class Database {
        private Connection conn;
        private Statement stmt;

        public Database() {
            //final String JDBC_DRIVER = "org.sqlite.Driver";
            final String DB_URL = "jdbc:sqlite:/Users/oscargarciavazquez/IdeaProjects/CsvToDatabase/src/Data/BookStore.db";

            final String USER = "";
            final String PASS = "";

            try {
                conn = DriverManager.getConnection(DB_URL, USER, PASS);
                stmt = conn.createStatement();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        /**
         * This method will return a Connection or create one if it does not exist yet.
         *
         * @return A connection.
         */
        public Connection getConn() {
            try {
                if (conn.isClosed()) {
                    //final String JDBC_DRIVER = "org.h2.Driver";
                    final String DB_URL = "jdbc:sqlite:/Users/oscargarciavazquez/IdeaProjects/CsvToDatabase/src/Data/BookStore.db";

                    final String USER = "";
                    final String PASS = "";

                    conn = DriverManager.getConnection(DB_URL, USER, PASS);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return conn;
        }

        /**
         * This method will return a Statement or create one if it does not exist yet.
         *
         * @return A statement.
         * @param sqlQuery
         */
        public Statement getStmt(String sqlQuery) {
            try {
                if (stmt.isClosed()) {
                    stmt = getConn().createStatement();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return stmt;
        }

    }

}
