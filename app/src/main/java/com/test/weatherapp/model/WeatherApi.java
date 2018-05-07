package com.test.weatherapp.model;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static com.test.weatherapp.model.AppConstants.*;

public interface WeatherApi {
//Retrofit
    @GET(ENDPOINT_WEATHER_BY_ZIP)
    Single<WeatherResponse> getWeatherOfZip(@Query(PARAM_WEATHER_ZIP) String zip);
}
