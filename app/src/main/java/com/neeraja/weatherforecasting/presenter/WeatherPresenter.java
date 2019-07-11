package com.neeraja.weatherforecasting.presenter;

import com.neeraja.weatherforecasting.asynctasks.WeatherAsyncTask;
import com.neeraja.weatherforecasting.contract.WeatherActivityContract;
import com.neeraja.weatherforecasting.model.WeatherDayModel;
import com.neeraja.weatherforecasting.model.WeatherModel;
import com.neeraja.weatherforecasting.utils.Globals;
import com.neeraja.weatherforecasting.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class WeatherPresenter implements WeatherActivityContract.Presenter {
    private WeatherActivityContract.View mView;

    public WeatherPresenter(WeatherActivityContract.View view) {
        mView = view;
    }

    @Override
    public void onClick(WeatherActivityContract.View view) {
        mView.onWeatherRequest();
    }

    @Override
    public void onWeatherRequest(String apiKey, String q) {
        //call the asynctask here and set the adapter and hide loading
        WeatherAsyncTask weatherAsyncTask = new WeatherAsyncTask(apiKey, q);
        WeatherModel weatherModel = null;
        try {
            weatherModel = (WeatherModel) weatherAsyncTask.execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        if (weatherModel != null) {
            mView.setCurrentData(weatherModel);
            List<WeatherDayModel> weatherDayModels = weatherModel.getWeatherDayModelList();
            if (Utils.isValidArrayList((ArrayList<?>) weatherDayModels)) {
                mView.setWeatherData(weatherDayModels);
            } else if (Utils.isValidString(Globals.lastErrMsg)) {
                mView.setErrorView(Globals.lastErrMsg);
            }
        } else if (Utils.isValidString(Globals.lastErrMsg)) {
            mView.setErrorView(Globals.lastErrMsg);
        }
    }

    @Override
    public void onRetryClicked() {
        mView.onWeatherRequest();
    }

    @Override
    public void onWeatherPageLaunch() {
        mView.onWeatherRequest();
    }
}
