package com.test.weatherapp.model;

public class Sys {

    private String message;
    private String id;
    private String sunset;
    private String sunrise;
    private String type;
    private String country;

    public String getMessage() {
        return message;
    }

    public String getId() {
        return id;
    }

    public String getSunset() {
        return sunset;
    }

    public String getSunrise() {
        return sunrise;
    }

    public String getType() {
        return type;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public String toString() {
        return "Sys{" +
                "message='" + message + '\'' +
                ", id='" + id + '\'' +
                ", sunset='" + sunset + '\'' +
                ", sunrise='" + sunrise + '\'' +
                ", type='" + type + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
