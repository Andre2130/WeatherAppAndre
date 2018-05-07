package com.test.weatherapp.model;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherDataManager {

    private WeatherApi mWeatherApi;

    public WeatherApi getWeatherApi() {
        if (mWeatherApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(AppConstants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            mWeatherApi = retrofit.create(WeatherApi.class);
        }
        return mWeatherApi;
    }
}
