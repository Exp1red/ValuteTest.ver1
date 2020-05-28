package org.example.valute;

import java.util.Map;

class Json {

    private String Date;
    private String PreviousDate;
    private String PreviousURL;
    private String Timestamp;
    private Map<String, Valute> Valute;

    public String getDate() {
        return Date;
    }

    public String getPreviousDate() {
        return PreviousDate;
    }

    public String getPreviousURL() {
        return PreviousURL;
    }

    public String getTimestamp() {
        return Timestamp;
    }

    public Map<String, org.example.valute.Valute> getValute() {
        return Valute;
    }
}

class Valute {

    private String ID;
    private int NumCode;
    private String CharCode;
    private int Nominal;
    private String Name;
    private double Value;
    private double Previous;

    @Override
    public String toString() {
        return CharCode + "\n" +
                " ID='" + ID + '\'' +
                " NumCode=" + NumCode + "\n" +
                " CharCode='" + CharCode + '\'' + "\n" +
                " Nominal=" + Nominal + "\n" +
                " Name='" + Name + '\'' + "\n" +
                " Value=" + Value + "\n" +
                " Previous=" + Previous + "\n";
    }

    public String getID() {
        return ID;
    }

    public int getNumCode() {
        return NumCode;
    }

    public String getCharCode() {
        return CharCode;
    }

    public int getNominal() {
        return Nominal;
    }

    public String getName() {
        return Name;
    }

    public double getValue() {
        return Value;
    }

    public double getPrevious() {
        return Previous;
    }
}
