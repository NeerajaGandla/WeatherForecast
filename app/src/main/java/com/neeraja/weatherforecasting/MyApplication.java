package com.neeraja.weatherforecasting;

import android.app.Application;

import com.neeraja.weatherforecasting.utils.FontsOverride;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FontsOverride.setDefaultFont(getApplicationContext(), "SERIF", "fonts/centurygothic.ttf");
    }
}
