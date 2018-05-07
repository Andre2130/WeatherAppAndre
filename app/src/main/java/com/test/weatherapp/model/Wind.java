package com.test.weatherapp.model;

public class Wind {

    private String gust;
    private String speed;
    private String deg;

    public String getGust() {
        return gust;
    }

    public String getSpeed() {
        return speed;
    }

    public String getDeg() {
        return deg;
    }

    @Override
    public String toString() {
        return "Wind{" +
                "gust='" + gust + '\'' +
                ", speed='" + speed + '\'' +
                ", deg='" + deg + '\'' +
                '}';
    }
}
