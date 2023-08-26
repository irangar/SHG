package com.isoft.smarthomegardening;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private static final int SPLASH_TIME_OUT = 6000;
    Window window;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        window = com.isoft.smarthomegardening.MainActivity.this.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(Color.rgb(0, 46, 43));
        }

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
            finish();
        },SPLASH_TIME_OUT);

    }
}