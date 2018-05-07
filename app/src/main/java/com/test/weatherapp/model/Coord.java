package com.test.weatherapp.model;

public class Coord {

    private String lat;

    public String getLat() {
        return lat;
    }

    @Override
    public String toString() {
        return "Coord{" +
                "lat='" + lat + '\'' +
                '}';
    }
}
