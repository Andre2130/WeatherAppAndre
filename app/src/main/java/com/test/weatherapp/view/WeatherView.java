package com.test.weatherapp.view;

import com.test.weatherapp.model.WeatherVO;

public interface WeatherView {

    void onShow(WeatherVO weatherVO);

    void onFail(String message);
}
