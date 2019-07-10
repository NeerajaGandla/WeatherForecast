package com.neeraja.weatherforecasting.view.activities;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.neeraja.weatherforecasting.R;
import com.neeraja.weatherforecasting.contract.WeatherActivityContract;
import com.neeraja.weatherforecasting.model.WeatherDayModel;
import com.neeraja.weatherforecasting.model.WeatherModel;
import com.neeraja.weatherforecasting.presenter.WeatherPresenter;
import com.neeraja.weatherforecasting.utils.GatherLocation;
import com.neeraja.weatherforecasting.utils.Utils;
import com.neeraja.weatherforecasting.view.adapters.WeatherAdapter;

import java.util.ArrayList;
import java.util.List;

public class WeatherActivity extends AppCompatActivity implements WeatherActivityContract.View {

    private WeatherPresenter weatherPresenter;
    private RecyclerView weatherRv;
    private Context mContext;
    private WeatherAdapter weatherAdapter;
    private TextView currentTemperatureTv, currentLocationTv;
    private ProgressBar progressBar;
    private Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        mContext = WeatherActivity.this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        progressBar = (ProgressBar) findViewById(R.id.loading);

        weatherPresenter = new WeatherPresenter(this);
        weatherRv = (RecyclerView) findViewById(R.id.rv_weather);
        weatherRv.setLayoutManager(new LinearLayoutManager(mContext));
        weatherRv.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));

        currentLocationTv = (TextView) findViewById(R.id.tv_current_location);
        currentTemperatureTv = (TextView) findViewById(R.id.tv_current_temperature);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weatherPresenter.onClick(WeatherActivity.this);
            }
        });

        onWeatherRequest();
    }

    @Override
    public void showLoading() {
        weatherRv.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
        weatherRv.setVisibility(View.VISIBLE);
    }

    @Override
    public void onWeatherRequest() {
        if (Utils.getConnectivityStatus(mContext)) {
            showLoading();
            String apiKey = getString(R.string.api_key);
            String q = "";
            GatherLocation gatherLocation = new GatherLocation();
            location = gatherLocation.getLocation(mContext);
            if (location != null) {
                q = location.getLatitude() + "," + location.getLongitude();
            }
            weatherPresenter.onWeatherRequest(apiKey, q);
        }
    }

    @Override
    public void setCurrentData(WeatherModel weatherModel) {
        currentTemperatureTv.setText((int)weatherModel.getCurrentTempCentigrade()+ getString(R.string.centigrade));
        currentLocationTv.setText(weatherModel.getLocationName());
    }

    @Override
    public void setWeatherData(List<WeatherDayModel> weatherDayModelList) {
        weatherAdapter = new WeatherAdapter((ArrayList<WeatherDayModel>) weatherDayModelList);
        weatherRv.setAdapter(weatherAdapter);
        hideLoading();
    }
}
