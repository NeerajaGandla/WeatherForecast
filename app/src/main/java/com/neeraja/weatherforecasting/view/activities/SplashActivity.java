package com.neeraja.weatherforecasting.view.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.neeraja.weatherforecasting.R;
import com.neeraja.weatherforecasting.contract.SplashActivityContract;
import com.neeraja.weatherforecasting.presenter.SplashPresenter;

public class SplashActivity extends AppCompatActivity implements SplashActivityContract.View {

    private boolean isAnimEnded;
    private Context mContext;
    private SplashPresenter splashPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = SplashActivity.this;
        splashPresenter = new SplashPresenter(this);

        RotateAnimation rotate = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(5000);
        rotate.setInterpolator(new LinearInterpolator());

        ImageView image = (ImageView) findViewById(R.id.iv_splash);
        rotate.setAnimationListener(listener);
        image.startAnimation(rotate);
    }

    private Animation.AnimationListener listener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
            isAnimEnded = false;
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            isAnimEnded = true;
            splashPresenter.onAnimationFinish();
        }
    };

    @Override
    public void goToWeatherScreen() {
        startActivity(new Intent(mContext, WeatherActivity.class));
        finish();
    }

}
