package com.neeraja.weatherforecasting.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.UnknownHostException;

public class HttpRequest {
    public static Object getInputStreamFromUrl(String url, Class classOfT) throws CustomException{
        // InputStream content = null;
        Utils.logD("URL : " + url);
        try {
            HttpGet httpGet = new HttpGet(url);
            HttpClient httpclient = new DefaultHttpClient();

            Utils.logD("Log 2");
            HttpResponse response = httpclient.execute(httpGet);

            return Utils.parse(response.getEntity().getContent(), classOfT, false);

        } catch (UnknownHostException e) {
            Globals.lastErrMsg = Constants.SERVER_NOT_REACHABLE;
            throw new CustomException("", Constants.PROB_WITH_SERVER);
        } catch (Exception e) {
            Utils.logD(e.getMessage());
            Globals.lastErrMsg = Constants.DEVICE_CONNECTIVITY;
            throw new CustomException(Constants.DEVICE_CONNECTIVITY, "");
        }
    }

    public static String convertStreamToString(HttpResponse response) {
        String responseBody = null;
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            try {
                responseBody = EntityUtils.toString(entity);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseBody;
    }

}
