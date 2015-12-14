package com.example.leanne.mobileappdevelopmentassignment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by Leanne on 15. 11. 26..
 */
public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Handler hd = new Handler();
        hd.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 3000); // 3초 후 이미지를 닫습니다
    }

}
