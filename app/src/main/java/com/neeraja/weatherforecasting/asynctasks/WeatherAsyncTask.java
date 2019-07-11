package com.neeraja.weatherforecasting.asynctasks;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.neeraja.weatherforecasting.R;
import com.neeraja.weatherforecasting.model.WeatherDayModel;
import com.neeraja.weatherforecasting.model.WeatherModel;
import com.neeraja.weatherforecasting.utils.ApiUtils;
import com.neeraja.weatherforecasting.utils.Constants;
import com.neeraja.weatherforecasting.utils.CustomException;
import com.neeraja.weatherforecasting.utils.Globals;
import com.neeraja.weatherforecasting.utils.HttpRequest;
import com.neeraja.weatherforecasting.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WeatherAsyncTask extends AsyncTask {
    private String apiKey;
    private String q;
    private Gson gson = new Gson();

    public WeatherAsyncTask(String apiKey, String q) {
        this.apiKey = apiKey;
        this.q = q;
    }

    @Override
    protected WeatherModel doInBackground(Object[] objects) {
        WeatherModel weatherModel = null;
        String url = ApiUtils.getWeatherForecastUrl(apiKey, q, Constants.NO_OF_DAYS);
        JSONObject response = null;
        Globals.lastErrMsg = "";
        try {
            LinkedTreeMap<?, ?> object = (LinkedTreeMap) HttpRequest.
                    getInputStreamFromUrl(url, LinkedTreeMap.class);
            Utils.logD(object.toString());
            String json = gson.toJson(object, LinkedTreeMap.class);
            Utils.logD("json : " + json.toString());
            response = new JSONObject(json);
            if (response != null) {
                Utils.logD("jsonObject :" + response.toString());
                JSONObject location = (JSONObject) response.get("location");
                JSONObject currentWeather = (JSONObject) response.get("current");
                weatherModel = new WeatherModel();
                if (location != null)
                    weatherModel.setLocationName(location.getString("name"));
                if (currentWeather != null)
                    weatherModel.setCurrentTempCentigrade(currentWeather.getDouble("temp_c"));
                JSONObject forecast = (JSONObject) response.get("forecast");
                List<WeatherDayModel> weatherDayModels = new ArrayList<>();
                if (forecast != null) {
                    JSONArray forecastArray = forecast.getJSONArray("forecastday");
                    for (int i = 0; i < forecastArray.length(); i++) {
                        WeatherDayModel weatherDayModel = new WeatherDayModel();
                        JSONObject dayWeatherObj = forecastArray.getJSONObject(i);
                        if (dayWeatherObj != null) {
                            JSONObject dayObj = dayWeatherObj.getJSONObject("day");
                            if (dayObj != null) {
                                weatherDayModel.setAvgTempCentigrade(dayObj.getDouble("avgtemp_c"));
                                weatherDayModel.setMinTempCentigrade(dayObj.getDouble("mintemp_c"));
                                weatherDayModel.setMaxTempCentigrade(dayObj.getDouble("maxtemp_c"));
                                JSONObject conditionObj = dayObj.getJSONObject("condition");
                                if (conditionObj != null) {
                                    weatherDayModel.setConditionText(conditionObj.getString("text"));
                                    weatherDayModel.setConditionIconUrl(conditionObj.getString("icon"));
                                    weatherDayModel.setConditionCode(conditionObj.getInt("code"));
                                }
                            }
                            weatherDayModel.setWeatherDate(dayWeatherObj.getString("date"));
                        }
                        weatherDayModels.add(weatherDayModel);
                    }
                } else {
                    Globals.lastErrMsg = Constants.N0_DATA;
                }
                weatherModel.setWeatherDayModelList(weatherDayModels);
            } else {
                Globals.lastErrMsg = Constants.N0_DATA;
            }
        } catch (CustomException e) {
            e.printStackTrace();
            Globals.lastErrMsg = Constants.SOMETHING_WENT_WRONG;
        } catch (JSONException e) {
            e.printStackTrace();
            Globals.lastErrMsg = Constants.ERROR_PARSING;
        }
        return weatherModel;//return the response WeatherModel here
    }
}
