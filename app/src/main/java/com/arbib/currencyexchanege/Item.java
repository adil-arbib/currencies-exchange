package com.arbib.currencyexchanege;

public class Item {
    private String name;
    private String rate;
    private double difference;
    private String fullname;

    public Item(String name, String rate, double difference, String fullname) {
        this.name = name;
        this.rate = rate;
        this.difference = difference;
        this.fullname = fullname;
    }

    public String getName() {
        return name;
    }

    public String getRate() {
        return rate;
    }

    public double getDifference() {
        return difference;
    }

    public String getFullname(){ return fullname; }
}
