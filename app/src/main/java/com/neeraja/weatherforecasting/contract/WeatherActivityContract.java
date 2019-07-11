package com.neeraja.weatherforecasting.contract;

import com.neeraja.weatherforecasting.model.WeatherDayModel;
import com.neeraja.weatherforecasting.model.WeatherModel;

import java.util.List;

public interface WeatherActivityContract {

    interface View {
        void showLoading();
        void hideLoading();
        void onWeatherRequest();
        void setCurrentData(WeatherModel weatherModel);
        void setWeatherData(List<WeatherDayModel> weatherDayModelList);
        void setErrorView(String message);
    }

    interface Presenter {
        void onClick(View view);
        void onWeatherRequest(String apiKey, String q);
        void onRetryClicked();
        void onWeatherPageLaunch();
    }

    interface Model {
        String toString();
    }
}
