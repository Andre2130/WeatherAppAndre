package com.test.weatherapp.presenter;

import com.google.gson.Gson;
import com.test.weatherapp.model.WeatherApi;
import com.test.weatherapp.model.WeatherDataManager;
import com.test.weatherapp.model.WeatherResponse;
import com.test.weatherapp.model.WeatherVO;
import com.test.weatherapp.view.WeatherView;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.concurrent.Callable;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.functions.Function;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WeatherPresenterTest {

    public static final String TEST_ZIP = "zip-123";
    public static final String TEST_ERROR = "Error";
    @Mock
    private WeatherView mView;
    @Mock
    private WeatherDataManager mDataManager;

    private WeatherPresenter mPresenter;

    @Before
    public void setUp() throws Exception {

        RxAndroidPlugins.reset();
        RxAndroidPlugins.setMainThreadSchedulerHandler(new Function<Scheduler, Scheduler>() {
            @Override
            public Scheduler apply(Scheduler scheduler) throws Exception {
                return Schedulers.trampoline();
            }
        });
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(new Function<Callable<Scheduler>, Scheduler>() {
            @Override
            public Scheduler apply(Callable<Scheduler> schedulerCallable) throws Exception {
                return Schedulers.trampoline();
            }
        });
        RxJavaPlugins.reset();
        RxJavaPlugins.setComputationSchedulerHandler(new Function<Scheduler, Scheduler>() {
            @Override
            public Scheduler apply(Scheduler scheduler) throws Exception {
                return Schedulers.trampoline();
            }
        });
        RxJavaPlugins.setIoSchedulerHandler(new Function<Scheduler, Scheduler>() {
            @Override
            public Scheduler apply(Scheduler scheduler) throws Exception {
                return Schedulers.trampoline();
            }
        });
        mPresenter = new WeatherPresenter(mView, mDataManager);
    }

    @Test
    public void getWeatherOfZip_Success() {

        SingleObserver observer = mock(SingleObserver.class);

        WeatherResponse response = new Gson().fromJson(DATA, WeatherResponse.class);

        Single<WeatherResponse> single = Single.just(response);
        single.subscribe(observer);

        WeatherApi api = mock(WeatherApi.class);
        when(api.getWeatherOfZip(anyString())).thenReturn(single);

        when(mDataManager.getWeatherApi()).thenReturn(api);

        mPresenter.getWeatherOfZip(TEST_ZIP);

        verify(mView, times(1)).onShow(any(WeatherVO.class));
    }

    @Test
    public void getWeatherOfZip_Fail() {

        SingleObserver observer = mock(SingleObserver.class);

        Throwable exception = new Throwable(TEST_ERROR);
        Single<WeatherResponse> single = Single.error(exception);
        single.subscribe(observer);

        WeatherApi api = mock(WeatherApi.class);
        when(api.getWeatherOfZip(anyString())).thenReturn(single);

        when(mDataManager.getWeatherApi()).thenReturn(api);

        mPresenter.getWeatherOfZip(TEST_ZIP);

        verify(mView, times(1)).onFail(exception.getMessage());
    }

    @Test
    public void cleanup() {
    }

    @After
    public void tearDown() throws Exception {
        RxJavaPlugins.reset();
        RxAndroidPlugins.reset();
        mPresenter.cleanup();
        mPresenter = null;
    }

    private final String DATA = "{\n" +
            "\"coord\": {\n" +
            "\"lon\": -0.13,\n" +
            "\"lat\": 51.51\n" +
            "},\n" +
            "\"weather\": [\n" +
            "{\n" +
            "\"id\": 300,\n" +
            "\"main\": \"Drizzle\",\n" +
            "\"description\": \"light intensity drizzle\",\n" +
            "\"icon\": \"09d\"\n" +
            "}\n" +
            "],\n" +
            "\"base\": \"stations\",\n" +
            "\"main\": {\n" +
            "\"temp\": 280.32,\n" +
            "\"pressure\": 1012,\n" +
            "\"humidity\": 81,\n" +
            "\"temp_min\": 279.15,\n" +
            "\"temp_max\": 281.15\n" +
            "},\n" +
            "\"visibility\": 10000,\n" +
            "\"wind\": {\n" +
            "\"speed\": 4.1,\n" +
            "\"deg\": 80\n" +
            "},\n" +
            "\"clouds\": {\n" +
            "\"all\": 90\n" +
            "},\n" +
            "\"dt\": 1485789600,\n" +
            "\"sys\": {\n" +
            "\"type\": 1,\n" +
            "\"id\": 5091,\n" +
            "\"message\": 0.0103,\n" +
            "\"country\": \"GB\",\n" +
            "\"sunrise\": 1485762037,\n" +
            "\"sunset\": 1485794875\n" +
            "},\n" +
            "\"id\": 2643743,\n" +
            "\"name\": \"London\",\n" +
            "\"cod\": 200\n" +
            "}";
}