package de.soerensc.dbconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class CONNECTION {
    private static String db_username;
    private static String db_password;
    private static String table;
    private static boolean logged_in;

    public CONNECTION() {
        db_username = "d036b6b5";
        db_password = "p1xoLuza21JLilu";
        table = "accounts";
        logged_in = true;
    }
    public static void main(String[] arr) throws SQLException {
        getConnection();
    }
    public static Connection getConnection() throws SQLException {
        new CONNECTION();
        Connection conn = null;
        Properties connectionProps = new Properties();
        connectionProps.put("user", db_username);
        connectionProps.put("password", db_password);

        conn = DriverManager.getConnection(
                "jdbc:mysql://javascriptcoding.org:3306/" + db_username,
                connectionProps);


        System.out.println("Connected to database");
        return conn;
    }
    public boolean check_for_acc(String check_username) throws SQLException {
        String query = "SELECT * FROM " + table + " WHERE user_name='" + check_username + "'";
        Statement stmt = getConnection().createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            return false;
        }
        return true;
    }
    public boolean create_acc(String new_username, String new_password) throws SQLException {
        if(check_for_acc(new_username)) {
            String query = "INSERT INTO " + table + "(user_name, user_password, user_level, user_coins, user_highscore) VALUES ('" + new_username + "','" + new_password + "',32,324324,546534)";
            Statement stmt = getConnection().createStatement();
            boolean rs = stmt.execute(query);

            System.out.println("New account with the username '" + new_username + "' was added!");
            System.out.println();

            stmt.close();
            login(new_username, new_password);

            return true;
        } else {
            System.out.println("Username already taken!");

            return false;
        }
    }
    public void get_acc(String username) throws SQLException {
        if (logged_in) {
            String query = "SELECT * FROM " + table + " WHERE user_name='" + username + "'";
            Statement stmt = getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);

            int columns = rs.getMetaData().getColumnCount();
            System.out.println("All results to the user with the name '" + username + "'.");
            System.out.println();

            for (int i = 1; i <= columns; i++)
                System.out.print(rs.getMetaData().getColumnLabel(i) + "\t");
            System.out.println();

            while (rs.next()) {
                for (int i = 1; i <= columns; i++) {
                    System.out.print(rs.getString(i) + "\t\t");
                }
                System.out.println();
                System.out.println();
            }

            rs.close();
            stmt.close();
        } else {
            System.out.println("You are not logged in!");
        }
    }
    public boolean login(String login_username, String login_password) throws SQLException {
        if(!check_for_acc(login_username)) {
            String query = "SELECT * FROM " + table + " WHERE user_name='" + login_username + "'";
            Statement stmt = getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                if(login_password.equals(rs.getString(3))) {
                    System.out.println("You're logged in!");
                    logged_in = true;
                    rs.close();
                    stmt.close();
                    return true;
                } else {
                    System.out.println("Wrong username or password!");
                    return false;
                }
            }
            System.out.println("Wrong username or password!");
            rs.close();
            stmt.close();
        } else {
            System.out.println("Username doesn't exist!");
        }
        return false;
    }
    public void new_highscore(String username, int new_highscore) throws SQLException {
        if(logged_in) {
            if(!check_for_acc(username)) {
                String query = "UPDATE accounts SET user_highscore='" + new_highscore + "' WHERE user_name='" + username + "'";
                Statement stmt = getConnection().createStatement();
                boolean rs = stmt.execute(query);

                System.out.println("New highscore for the user with the username '" + username + "' was added!");
                System.out.println();

                stmt.close();
            } else {
                System.out.println("Username doesn't exist!");
            }
        } else {
            System.out.println("You are not logged in!");
        }
    }
    public void update_coins(String username, int coins) throws SQLException {
        if(logged_in) {
            if(!check_for_acc(username)) {
                String query = "UPDATE accounts SET user_coins='" + coins + "' WHERE user_name='" + username + "'";
                Statement stmt = getConnection().createStatement();
                boolean rs = stmt.execute(query);

                System.out.println("Current amount of coins for the user with the username '" + username + "' was updated!");
                System.out.println();

                stmt.close();
            } else {
                System.out.println("Username doesn't exist!");
            }
        } else {
            System.out.println("You are not logged in!");
        }
    }
    public boolean get_logged_in() {
        return logged_in;
    }
}
