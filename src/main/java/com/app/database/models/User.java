package com.app.database.models;

import com.app.database.DatabaseHandler;
import com.app.services.HashPasswordMD5;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    private int id;
    private String login;
    private String password;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public User() {

    }

    public User(int id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public int getId() {
        return id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static User get(String login, String password) {
        ResultSet resSet = null;

        String select = "SELECT * FROM user WHERE login = ? and password = ?";

        try {
            PreparedStatement prSt = DatabaseHandler.getDbConnection().prepareStatement(select);
            prSt.setString(1, login);
            prSt.setString(2, HashPasswordMD5.hash_password(password));
            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            assert resSet != null;
            if (resSet.next())
            {
                return getFromResultSet(resSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static User get(String login) {
        ResultSet resSet = null;

        String select = "SELECT * FROM user WHERE login = ?";

        try {
            PreparedStatement prSt = DatabaseHandler.getDbConnection().prepareStatement(select);
            prSt.setString(1, login);
            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            assert resSet != null;
            if (resSet.next()) {
                return getFromResultSet(resSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void register(String login, String password) {
        String insert = "INSERT INTO user (login, password) VALUES (?, ?)";

        try {
            PreparedStatement prSt = DatabaseHandler.getDbConnection().prepareStatement(insert);
            prSt.setString(1, login);
            prSt.setString(2, HashPasswordMD5.hash_password(password));
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static void updateLogin(int id, String newLogin) {
        String insert = "UPDATE user SET login = ? WHERE id = ?";

        try {
            PreparedStatement prSt = DatabaseHandler.getDbConnection().prepareStatement(insert);
            prSt.setString(1, newLogin);
            prSt.setString(2, String.valueOf(id));
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void updatePassword(int id, String newPassword) {
        String insert = "UPDATE user SET password = ? WHERE id = ?";

        try {
            PreparedStatement prSt = DatabaseHandler.getDbConnection().prepareStatement(insert);
            prSt.setString(2, String.valueOf(id));
            prSt.setString(1, HashPasswordMD5.hash_password(newPassword));
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static User getFromResultSet(ResultSet resSet) {
        try {
            int id = resSet.getInt("id");
            String login = resSet.getString("login");
            String password = resSet.getString("password");
            return new User(id, login, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
