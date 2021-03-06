package com.neeraja.weatherforecasting.utils;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.format.DateUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.neeraja.weatherforecasting.BuildConfig;

import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.zip.GZIPInputStream;

public class Utils {
    private static Gson gson = new Gson();

    public static String getTime(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static boolean isTomorrow(Date date) {
        DateTime tomorrow = new DateTime().withTimeAtStartOfDay().plusDays(1);
        DateTime dateTime = new DateTime(date);
        DateTime inputDay = dateTime.withTimeAtStartOfDay();
        return inputDay.isEqual(tomorrow);
    }

    public static boolean checkForGPS(Context context) {
        final LocationManager manager = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER) && !manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
            return false;
        else
            return true;
    }

    public static boolean isToday(long millis) {
        return DateUtils.isToday(millis);
    }

    public static boolean isValidString(String str) {
        if (str != null) {
            str = str.trim();
            if (str.length() > 0)
                return true;
        }
        return false;
    }

    public static boolean getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null
                && activeNetwork.isConnected();
        return isConnected;
    }

    public static boolean isValidArrayList(ArrayList<?> list) {
        if (list != null && list.size() > 0) {
            return true;
        }
        return false;
    }

    public static void logE(String msg) {
        if (BuildConfig.DEBUG) {
            Log.e(Constants.LOG_TAG, msg);
        }
    }

    public static void logI(String msg) {
        if (BuildConfig.DEBUG) {
            Log.i(Constants.LOG_TAG, msg);
        }
    }

    public static void logD(String msg) {
        if (BuildConfig.DEBUG) {
            Log.d(Constants.LOG_TAG, msg);
        }
    }

    public static Date getDate(String time, String format) {
        Date date = null;
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        try {
            date = (Date) formatter.parseObject(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    public static Object parseResp(InputStream is, Class<?> classOfT)
            throws Exception {
        try {
            Reader readr = new InputStreamReader(is);
            return gson.fromJson(readr, classOfT);
        } catch (Exception e) {
            Utils.logE(e.toString());
            throw new CustomException(Constants.ERROR_PARSING,
                    Constants.DATA_INVALID);
        }
    }

    public static Object parse(InputStream is, Class<?> classOfT, boolean isGzip)
            throws Exception {
        try {
            if (isGzip) {
                InputStream ist = new BufferedInputStream(new GZIPInputStream(
                        is));

                logD("Gzipped");
                String s = IOUtils.toString(ist, "UTF-8");

                s = s.replaceAll("[\\x00-\\x1F\\x80-\\xFF]", "");
                is.close();
                return gson.fromJson(s, classOfT);
            } else {
                Reader readr = new InputStreamReader(is);
                return gson.fromJson(readr, classOfT);
            }
            // Reader readr = new InputStreamReader(is);
            // // String s = IOUtils.toString(is, "UTF-8");
            // // Utils.logD(s);
            // return gson.fromJson(readr, classOfT);
        } catch (Exception e) {
            e.printStackTrace();
            Utils.logE(e.toString());
            Globals.lastErrMsg = Constants.ERROR_PARSING;
            throw new CustomException(Constants.ERROR_PARSING,
                    Constants.DATA_INVALID);
            // throw e;
        }
    }

}
