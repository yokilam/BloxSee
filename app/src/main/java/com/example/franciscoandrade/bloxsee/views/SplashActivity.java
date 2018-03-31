package com.example.franciscoandrade.bloxsee.views;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.franciscoandrade.bloxsee.R;

public class SplashActivity extends AppCompatActivity {
    ImageView gif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        gif= findViewById(R.id.gif);

        Handler handler= new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
            }
        },2800);

        Glide.with(SplashActivity.this)
                .load(R.drawable.newbloxseefont)
                .into(gif);
    }
}
