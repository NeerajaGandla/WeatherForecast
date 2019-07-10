package com.neeraja.weatherforecasting.presenter;

import android.view.View;

import com.neeraja.weatherforecasting.contract.SplashActivityContract;

public class SplashPresenter implements SplashActivityContract.Presenter {
    private SplashActivityContract.View mView;

    public SplashPresenter(SplashActivityContract.View view) {
        mView = view;
    }

    @Override
    public void onAnimationFinish() {
        mView.goToWeatherScreen();
    }
}
