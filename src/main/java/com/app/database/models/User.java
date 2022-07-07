package com.app.database.models;

import com.app.database.DatabaseHandler;
import com.app.services.HashPasswordMD5;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

public class User {
    private int id;
    private String login;
    private String password;

    private ArrayList<Integer> functions;

    private boolean isStaff;

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
    public User(int id, String login, String password, boolean isStaff) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.isStaff = isStaff;
    }

    public static ArrayList<User> all() {
        ResultSet resSet = null;

        String select = "SELECT * FROM user";

        try {
            PreparedStatement prSt = DatabaseHandler.getDbConnection().prepareStatement(select);
            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        ArrayList<User> arr = new ArrayList<>();
        try {
            while (resSet.next()) {
                arr.add(getFromResultSet(resSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return arr;
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

    public boolean isStaff() {
        return isStaff;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setStaff(boolean staff) {
        isStaff = staff;
        String insert = "UPDATE user SET is_staff = ? WHERE id = ?";
        try {
            PreparedStatement prSt = DatabaseHandler.getDbConnection().prepareStatement(insert);
            prSt.setInt(1, staff? 1: 0);
            prSt.setInt(2, id);
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setFunctions(ArrayList<Integer> functions) {
        this.functions = functions;

        String insert = "INSERT INTO access_control (user_id, function_id) VALUES (?,?)";

        for (Integer function: functions) {
            try {
                PreparedStatement prSt = DatabaseHandler.getDbConnection().prepareStatement(insert);
                prSt.setString(1, String.valueOf(id));
                prSt.setString(2, String.valueOf(function));
                prSt.executeUpdate();
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
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
            if (e.getClass() == SQLException.class){
                return null;
            }
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

    public static User register(String login, String password) {
        String insert = "INSERT INTO user (login, password) VALUES (?, ?)";

        try {
            PreparedStatement prSt = DatabaseHandler.getDbConnection().prepareStatement(insert);
            prSt.setString(1, login);
            prSt.setString(2, HashPasswordMD5.hash_password(password));
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            if (e.getClass() == SQLIntegrityConstraintViolationException.class){
                return null;
            }else {
                e.printStackTrace();
            }
        }
        return get(login,password);

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

    public int quotesCount() throws SQLException {
        ResultSet resSet = null;

        String select = "SELECT author, COUNT(quote) as c FROM lecturer_quotes WHERE author=? GROUP BY author";

        try {
            PreparedStatement prSt = DatabaseHandler.getDbConnection().prepareStatement(select);
            prSt.setString(1, String.valueOf(id));
            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        assert resSet != null;
        return resSet.next()? resSet.getInt("c") : 0;

    }

    public ArrayList<Integer> getFunctions() {
        return functions==null? new ArrayList<>(): functions;
    }

    public ArrayList<Integer> getFunctionsFromDB() throws SQLException{
        ResultSet resSet = null;
        ArrayList<Integer> functions = new ArrayList<Integer>();
        String select = "SELECT function_id FROM access_control WHERE user_id = ?";

        try {
            PreparedStatement prSt = DatabaseHandler.getDbConnection().prepareStatement(select);
            prSt.setString(1, String.valueOf(id));
            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        assert resSet != null;
        while (resSet.next()){
            functions.add(resSet.getInt("function_id"));
        }
        return functions.isEmpty()? null: functions;
    }

    public void deleteFunction(int function_id){
        String delete = "DELETE FROM access_control WHERE function_id = ? AND user_id = ?";

        try {
            PreparedStatement prSt = DatabaseHandler.getDbConnection().prepareStatement(delete);
            prSt.setString(1, String.valueOf(function_id));
            prSt.setString(2, String.valueOf(id));
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
            int isStaff = resSet.getInt("is_staff");
            boolean staff = isStaff == 1;
            return new User(id, login, password, staff);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
