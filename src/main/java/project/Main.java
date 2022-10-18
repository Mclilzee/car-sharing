package project;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
            Class.forName ("org.h2.Driver");
            Connection conn = DriverManager.getConnection("jdbc:h2:./database/carsharing");
            conn.setAutoCommit(true);
            Statement st = conn.createStatement();
            st.executeUpdate("CREATE TABLE IF NOT EXISTS COMPANY (ID INT, NAME VARCHAR)");
            conn.close();
    }
}