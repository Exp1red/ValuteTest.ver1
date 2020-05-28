package org.example.valute;

import java.sql.*;
import java.util.Map;
// table in Base = name , nominal , value , previousValue , USD , EUR ,CNY ,JPY , id
public class Dao {
    private final Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test",
            "postgres", "qwerty");

    private Map<Valute, Map<String, Double>> statistic;

    public Dao(Map<Valute, Map<String, Double>> ratio) throws SQLException {
        this.statistic = ratio;
    }

    public Dao() throws SQLException {}


    public  void saveComplexStatistic() {

        try (Statement statement = connection.createStatement()) {

            for (Valute valute : statistic.keySet()) {
                String valuteName = valute.getCharCode();
                StringBuilder builder = new StringBuilder();
                builder.append(valute.getNominal()).append(",");
                builder.append(valute.getValue()).append(",");
                builder.append(valute.getPrevious()).append(",");

                for (String name : statistic.get(valute).keySet()) {
                    builder.append(statistic.get(valute).get(name)).append(",");
                }
                builder.deleteCharAt(builder.length() - 1);
                statement.execute("INSERT INTO valutes VALUES (" + (char) 39 + valuteName + (char) 39 + "," +
                        builder.toString() + ")");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public Map<Valute, Map<String, Double>> getStatistic() {
        return statistic;
    }
}
