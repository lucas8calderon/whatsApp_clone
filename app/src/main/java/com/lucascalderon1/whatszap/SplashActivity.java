package com.lucascalderon1.whatszap;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private ImageView img;
    private TextView textView7,textView8;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initialWork();
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.splash_screen);
        img.setAnimation(anim);
        textView7.setAnimation(anim);
        textView8.setAnimation(anim);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                ActivityLogin();

            }
        }, 3000); // 3 segundos
    }

    private void initialWork() {
        img = findViewById(R.id.imageView4);
        textView7 = findViewById(R.id.textView7);
        textView8 = findViewById(R.id.textView8);


    }

    private void ActivityLogin() {
        Intent intent = new Intent(SplashActivity.this, com.lucascalderon1.whatszap.IntroActivity.class);
        startActivity(intent);

    }
}

