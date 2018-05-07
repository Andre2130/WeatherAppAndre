package com.test.weatherapp.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.test.weatherapp.R;
import com.test.weatherapp.model.WeatherVO;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class WeatherDetailActivity extends AppCompatActivity {

    public static final String EXTRA_WEATHER_DATA = "weather_data";

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_detail);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        mDrawerLayout = findViewById(R.id.navigationDrawer);
        mNavigationView = findViewById(R.id.navigationView);

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mDrawerLayout.closeDrawer(Gravity.START);
                Toast.makeText(WeatherDetailActivity.this, "Clicked on " + item.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.openDrawer, R.string.closeDrawer) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
                supportInvalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
                supportInvalidateOptionsMenu();
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        //retrieves information from serialized data
        WeatherVO data = (WeatherVO) getIntent().getSerializableExtra(EXTRA_WEATHER_DATA);
        if (data != null) {
            //data.mPressure
            //This is where I make all of the connections to the view

            TextView description  = findViewById(R.id.textView3);
            TextView windSpeed = findViewById(R.id.windSpeedTxt);
            TextView cloudiness = findViewById(R.id.cloudTxt);
            TextView pressure = findViewById(R.id.humidityTxt);
            TextView humidity = findViewById(R.id.pressureTxt);
            TextView sunrise = findViewById(R.id.sunriseTxt);

            description.setText(data.mDescription);
            windSpeed.setText(data.mWindSpeed);
            cloudiness.setText(data.mCloudiness);
            humidity.setText(data.mHumidity);
            pressure.setText(data.mPressure);
            sunrise.setText(getConverterDate(Long.parseLong(data.mSunrise)));

        }
    }

    //This converts the unixtimestamp that is returned by the API into military time
    public String getConverterDate(long dateTime)
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm a");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("AST"));

        return simpleDateFormat.format(new Date(dateTime * 1000L));


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
