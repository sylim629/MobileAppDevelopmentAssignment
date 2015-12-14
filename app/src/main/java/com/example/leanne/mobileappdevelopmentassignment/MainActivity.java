package com.example.leanne.mobileappdevelopmentassignment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private BackPressCloseHandler backPressCloseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startActivity(new Intent(this, SplashActivity.class));
        initView();
    }

    private void initView() {
        backPressCloseHandler = new BackPressCloseHandler(this);
        showFragment(0);
    }

    private void showFragment(int position) {
        Fragment fragment = new LoginActivity();
        FragmentManager fm = getFragmentManager();

        switch (position) {
            case 0:
                fragment = new LoginActivity();
                break;
            case 1:
                break;
        }
        fm.beginTransaction().replace(R.id.content_frame, fragment).commit();
    }

    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }
}
