package com.neeraja.weatherforecasting.model;

import com.neeraja.weatherforecasting.contract.WeatherActivityContract;

public class WeatherDayModel implements WeatherActivityContract.Model {
    private double minTempCentigrade;
    private double maxTempCentigrade;
    private double avgTempCentigrade;
    private String conditionIconUrl;
    private String conditionText;
    private int conditionCode;
    private String weatherDate;

    public double getMinTempCentigrade() {
        return minTempCentigrade;
    }

    public void setMinTempCentigrade(double minTempCentigrade) {
        this.minTempCentigrade = minTempCentigrade;
    }

    public double getMaxTempCentigrade() {
        return maxTempCentigrade;
    }

    public void setMaxTempCentigrade(double maxTempCentigrade) {
        this.maxTempCentigrade = maxTempCentigrade;
    }

    public double getAvgTempCentigrade() {
        return avgTempCentigrade;
    }

    public void setAvgTempCentigrade(double avgTempCentigrade) {
        this.avgTempCentigrade = avgTempCentigrade;
    }

    public String getConditionIconUrl() {
        return conditionIconUrl;
    }

    public void setConditionIconUrl(String conditionIconUrl) {
        this.conditionIconUrl = conditionIconUrl;
    }

    public String getConditionText() {
        return conditionText;
    }

    public void setConditionText(String conditionText) {
        this.conditionText = conditionText;
    }

    public int getConditionCode() {
        return conditionCode;
    }

    public void setConditionCode(int conditionCode) {
        this.conditionCode = conditionCode;
    }

    public String getWeatherDate() {
        return weatherDate;
    }

    public void setWeatherDate(String weatherDate) {
        this.weatherDate = weatherDate;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
