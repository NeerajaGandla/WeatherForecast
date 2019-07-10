package com.neeraja.weatherforecasting.utils;

public class ApiUtils {
    public static final String baseUrl = "http://api.apixu.com/v1/forecast.json";
    public static final String keyTag = "?key=";
    public static final String qTag = "&q=";
    public static final String daysTag = "&days=";

    public static String getWeatherForecastUrl(String apiKey, String q, int days) {
        return baseUrl + keyTag + apiKey + qTag + q + daysTag + days;
    }
}
