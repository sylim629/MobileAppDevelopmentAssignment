package com.example.leanne.mobileappdevelopmentassignment;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Leanne on 15. 11. 29..
 */
public class FindPasswordActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password);
    }

    @Override
    public void onBackPressed() {
        this.finish();
        overridePendingTransition(R.anim.animation_enter_left2right, R.anim.animation_leave_left2right);
    }
}
