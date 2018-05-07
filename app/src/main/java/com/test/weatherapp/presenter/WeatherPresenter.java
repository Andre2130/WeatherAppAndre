package com.test.weatherapp.presenter;

import com.test.weatherapp.model.Clouds;
import com.test.weatherapp.model.Main;
import com.test.weatherapp.model.Sys;
import com.test.weatherapp.model.Weather;
import com.test.weatherapp.model.WeatherApi;
import com.test.weatherapp.model.WeatherDataManager;
import com.test.weatherapp.model.WeatherResponse;
import com.test.weatherapp.model.WeatherVO;
import com.test.weatherapp.model.Wind;
import com.test.weatherapp.view.WeatherView;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class WeatherPresenter {

    private final CompositeDisposable disposable = new CompositeDisposable();

    private final WeatherView mWeatherView;
    private final WeatherDataManager mDataManager;

    public WeatherPresenter(WeatherView view, WeatherDataManager dataManager) {
        mWeatherView = view;
        mDataManager = dataManager;
    }
//Gets all information from the API
    public void getWeatherOfZip(String zip) {
        WeatherApi api = mDataManager.getWeatherApi();
        Single<WeatherVO> single = api.getWeatherOfZip(zip)
                .map(new Function<WeatherResponse, WeatherVO>() {
                    @Override
                    public WeatherVO apply(WeatherResponse weatherResponse) throws Exception {
                        Main main = weatherResponse.getMain();
                        Weather[] weather = weatherResponse.getWeather();
                        Wind wind = weatherResponse.getWind();
                        Clouds clouds = weatherResponse.getClouds();
                        Sys sys= weatherResponse.getSys();

                        WeatherVO weatherVO = new WeatherVO();
                        weatherVO.mDescription = weather[0].getDescription();
                        weatherVO.mWindSpeed = wind.getSpeed() + " m/s";
                        //The JSON for this only returned a number so I assumed it was a percentage
                        weatherVO.mCloudiness = clouds.getAll() + "%";
                        weatherVO.mPressure= main.getPressure()+ " hpa";
                        weatherVO.mHumidity= main.getHumidity() + " %";
                        weatherVO.mSunrise= sys.getSunrise();
                        return weatherVO;
                    }
                })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
        disposable.add(
                single.subscribeWith(new DisposableSingleObserver<WeatherVO>() {
                    @Override
                    public void onSuccess(WeatherVO weatherVO) {
                        mWeatherView.onShow(weatherVO);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mWeatherView.onFail(e.getMessage());
                    }
                }));
    }

    public void cleanup() {
        disposable.clear();
    }
}
