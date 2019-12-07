package com.ankur.example;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.ankur.example.ui.home.MeetingHOmeActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

    }


    @Override
    protected void onResume() {
        super.onResume();
        openNextScreen();

    }

    private void openNextScreen() {
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, MeetingHOmeActivity.class);
            startActivity(intent);
        }, 1500);
    }
}
