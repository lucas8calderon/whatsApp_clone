package com.lucascalderon1.whatszap;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class IntroActivity extends AppCompatActivity {

    private Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        button2 = findViewById(R.id.button2);

        button2.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), EnviarCodigoActivity.class);
            startActivity(intent);
            finish();
        });




    }
}