package com.stroganov;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Properties;

public class CandleStream {


    private final ArrayList<Candle> candlesArrayList = new ArrayList<>();

    public CandleStream(String fileName) {

        try (BufferedReader fileReader = new BufferedReader(new FileReader(fileName))) {
            String stringFromFile = fileReader.readLine();
            System.out.println(stringFromFile + " - Header of file");
            while ((stringFromFile = fileReader.readLine()) != null) {
                String[] buf = stringFromFile.split(",");

                candlesArrayList.add(new Candle(buf[0],
                        Integer.parseInt(buf[1]),
                        DateTimeFromString.GetDateFromString(buf[2]),
                        DateTimeFromString.GetTimeFromString(buf[3]),
                        Float.parseFloat(buf[4]),
                        Float.parseFloat(buf[5]),
                        Float.parseFloat(buf[6]), Float.parseFloat(buf[7]),
                        Float.parseFloat(buf[8])));

                if (candlesArrayList.isEmpty()) {
                    System.out.println("Error,Список свечей пуст");
                }
            }
        } catch (
                IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public CandleStream(String ticker, int period, LocalDate dateFrom, LocalDate dateTo) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();

        } catch (InstantiationException e) {
            e.printStackTrace();
            System.out.println("Error, Ошибка инициализации");
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (Connection conn = getConnection();) {

            System.out.println("Connection to Store DB succesfull!");
            Statement statement = conn.createStatement();
            getCandleArrayFromBase(ticker, period, dateFrom, dateTo, statement);
        } catch (SQLException | IOException ex) {
            System.out.println("Connection failed...");
            System.out.println(ex);
        }
    }

    private static Connection getConnection() throws SQLException, IOException {

        Properties props = new Properties();
        try (InputStream in = Files.newInputStream(Paths.get("database.properties"))) {
            props.load(in);
        }
        String url = props.getProperty("url");
        String username = props.getProperty("username");
        String password = props.getProperty("password");

        return DriverManager.getConnection(url, username, password);
    }


    private void getCandleArrayFromBase(String ticker, int period, LocalDate dateFrom, LocalDate dateTo, Statement statement) throws SQLException {

        Candle resultCandle = null;
        ResultSet resultSet = statement.executeQuery("SELECT * FROM cstream WHERE " +
                "Ticker ='" + ticker + "' AND " +
                "Per = " + period + " AND " +
                "Date BETWEEN '" + dateFrom.toString() + "' AND '" + dateTo.toString() + "'");
        // ORDER BY Date;
        while (resultSet.next()) {
            candlesArrayList.add(new Candle(
                    resultSet.getString("Ticker"),
                    resultSet.getInt("Per"),
                    resultSet.getDate("Date").toLocalDate(),
                    resultSet.getTime("Time").toLocalTime(),
                    resultSet.getFloat(6),
                    resultSet.getFloat(7),
                    resultSet.getFloat(8),
                    resultSet.getFloat(9),
                    resultSet.getFloat(10)));
        }
    }

    public ArrayList<Candle> getCandlesArrayList() {
        return candlesArrayList;
    }
}
