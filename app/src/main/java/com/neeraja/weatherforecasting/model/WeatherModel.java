package com.neeraja.weatherforecasting.model;

import com.neeraja.weatherforecasting.contract.WeatherActivityContract;

import java.util.List;

public class WeatherModel implements WeatherActivityContract.Model {
    private String locationName;
    private double currentTempCentigrade;
    private List<WeatherDayModel> weatherDayModelList;

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public double getCurrentTempCentigrade() {
        return currentTempCentigrade;
    }

    public void setCurrentTempCentigrade(double currentTempCentigrade) {
        this.currentTempCentigrade = currentTempCentigrade;
    }

    public List<WeatherDayModel> getWeatherDayModelList() {
        return weatherDayModelList;
    }

    public void setWeatherDayModelList(List<WeatherDayModel> weatherDayModelList) {
        this.weatherDayModelList = weatherDayModelList;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
