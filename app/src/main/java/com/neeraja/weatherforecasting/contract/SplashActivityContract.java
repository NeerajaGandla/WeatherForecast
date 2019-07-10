package com.neeraja.weatherforecasting.contract;

public interface SplashActivityContract {
    interface View {
        void goToWeatherScreen();
    }

    interface Presenter {
        void onAnimationFinish();
    }
}
