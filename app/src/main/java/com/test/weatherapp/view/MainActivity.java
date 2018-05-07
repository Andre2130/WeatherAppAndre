package com.test.weatherapp.view;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.test.weatherapp.R;
import com.test.weatherapp.model.WeatherDataManager;
import com.test.weatherapp.model.WeatherVO;
import com.test.weatherapp.presenter.WeatherPresenter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, WeatherView {

    private ProgressDialog mProgressDialog;
    private AlertDialog mAlertDialog;
    private EditText mZipCodeTxt;
    private WeatherPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mZipCodeTxt = findViewById(R.id.editText);
        mPresenter = new WeatherPresenter(MainActivity.this, new WeatherDataManager());

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCancelable(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
    }

    @Override
    public void onClick(View v) {
        dismissDialog();
        String zip = mZipCodeTxt.getText().toString();
        mPresenter.getWeatherOfZip(zip);
        mProgressDialog.show();
    }

    @Override
    public void onShow(WeatherVO weatherVO) {
        dismissDialog();
        Toast.makeText(this, "Weather received", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, WeatherDetailActivity.class);
        intent.putExtra(WeatherDetailActivity.EXTRA_WEATHER_DATA, weatherVO);
        startActivity(intent);
    }

    @Override
    public void onFail(String message) {
        dismissDialog();
        if (mAlertDialog == null) {
            mAlertDialog = new AlertDialog.Builder(this)
                    .setCancelable(true)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create();
        }
        if (mAlertDialog.isShowing()) {
            mAlertDialog.dismiss();
        }
        mAlertDialog.setMessage(message);
        mAlertDialog.show();
    }

    //loading popup
    private void dismissDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        mPresenter.cleanup();
        super.onDestroy();
    }
}
