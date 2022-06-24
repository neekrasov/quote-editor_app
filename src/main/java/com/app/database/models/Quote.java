package com.app.database.models;

import com.app.database.DatabaseHandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Quote {
    private int id;
    private String quote;
    private String lecturer;
    private String subject;
    private String date;

    public Quote(int id, String quote, String lecturer, String subject, String date) {
        this.id = id;
        this.quote = quote;
        this.lecturer = lecturer;
        this.subject = subject;
        this.date = date;
    }

    public static Quote create(String quotes, String lecturer, String subject, String date) {
        String insert = "INSERT INTO lecturer_quotes VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement prSt = DatabaseHandler.getDbConnection().prepareStatement(insert);
            prSt.setString(1, quotes);
            prSt.setString(2, lecturer);
            prSt.setString(3, subject);
            prSt.setString(4, date);
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return get(quotes, lecturer);
    }

    public static Quote get(String quote, String lecturer) {
        ResultSet resSet = null;

        String select = "SELECT * FROM lecturer_quotes WHERE quote = ? and lecturer = ?";

        try {
            PreparedStatement prSt = DatabaseHandler.getDbConnection().prepareStatement(select);
            prSt.setString(1, quote);
            prSt.setString(2, lecturer);
            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            assert resSet != null;
            resSet.next();
            return getFromResultSet(resSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Quote get(int id) {
        ResultSet resSet = null;

        String select = "SELECT * FROM lecturer_quotes WHERE id = ?";

        try {
            PreparedStatement prSt = DatabaseHandler.getDbConnection().prepareStatement(select);
            prSt.setString(1, String.valueOf(id));
            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            assert resSet != null;
            resSet.next();
            return getFromResultSet(resSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Quote getFromResultSet(ResultSet resSet) {
        try {
            int id = resSet.getInt("id");
            String quote = resSet.getString("quote");
            String lecturer = resSet.getString("lecturer");
            String subject = resSet.getString("subject");
            String date = resSet.getString("date");
            return new Quote(id, quote, lecturer, subject, date);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Quote> all() {
        ResultSet resSet = null;

        String select = "SELECT * FROM lecturer_quotes";

        try {
            PreparedStatement prSt = DatabaseHandler.getDbConnection().prepareStatement(select);
            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        ArrayList<Quote> arr = new ArrayList<>();
        try {
            while (resSet.next()) {
                arr.add(getFromResultSet(resSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return arr;
    }

    public int getId() {
        return id;
    }

    public String getQuote() {
        return quote;
    }

    public String getLecturer() {
        return lecturer;
    }

    public String getSubject() {
        return subject;
    }

    public String getDate() {
        return date;
    }
}
