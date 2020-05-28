package org.example.valute;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class Main {


    public static void main(String[] args) {
        Gson gson = new Gson();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new URL("https://www.cbr-xml-daily.ru/daily_json.js").openStream()))) {


            Json json = gson.fromJson(reader, Json.class);
            List<Valute> list = getSelection(json.getValute(), "USD", "EUR", "CNY", "JPY");
            Dao dao = new Dao(getRatio(list));
            dao.saveComplexStatistic();

        } catch (IOException | SQLException e) {
            e.getStackTrace();
        }
    }


    public static Map<Valute, Map<String, Double>> getRatio(List<Valute> list) {
        Map<Valute, Map<String, Double>> map = new LinkedHashMap<>();

        for (Valute valute : list) {
            Map<String, Double> subMap = new LinkedHashMap<>();
            for (Valute otherValute : list) {

                subMap.put(otherValute.getCharCode(),
                      new BigDecimal(valute.getValue() /*/ valute.getNominal()*/ / (otherValute.getValue() /*/ otherValute.getNominal()*/))
                              .setScale(4 , RoundingMode.UP).doubleValue());
            }
            map.put(valute, subMap);
        }
        return map;
    }


    public static List<Valute> getSelection(Map<String, Valute> map, String... args) {
        List<Valute> list = new ArrayList<>();
        for (String arg : args) {
            list.add(map.get(arg));
        }
        return list;
    }


}
