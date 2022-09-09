package com.lucascalderon1.whatszap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class EnviarCodigoActivity extends AppCompatActivity {


    EditText mgetphonenumber;
    Button msendotp;
    CountryCodePicker mcountrycodepicker;
    String countrycode;
    String phonenumber;

    FirebaseAuth firebaseAuth;
    ProgressBar progressbarsetNumero;

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBacks;
    String codesent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar_codigo);

        firebaseAuth = FirebaseAuth.getInstance();


        mcountrycodepicker = findViewById(R.id.countrycodepicker);
        msendotp = findViewById(R.id.sendotpbutton);
        mgetphonenumber = findViewById(R.id.getphonenumber);
        progressbarsetNumero = findViewById(R.id.sendotpprogressbar);


        countrycode = mcountrycodepicker.getSelectedCountryCodeWithPlus();

        mcountrycodepicker.setOnCountryChangeListener(() -> countrycode=mcountrycodepicker.getSelectedCountryCodeWithPlus());


        msendotp.setOnClickListener(v -> {
            String number;

            number=mgetphonenumber.getText().toString();
            if (number.isEmpty()){
                Toast.makeText(EnviarCodigoActivity.this, "Informe seu número!", Toast.LENGTH_SHORT).show();
            }

            else if (number.length()<10) {

                Toast.makeText(EnviarCodigoActivity.this, "Quantidade de números incorretos!", Toast.LENGTH_SHORT).show();

            }

            else  {
                progressbarsetNumero.setVisibility(View.VISIBLE);
                phonenumber=countrycode+number;

               PhoneAuthOptions options = PhoneAuthOptions.newBuilder(firebaseAuth)
                        .setPhoneNumber(phonenumber)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(EnviarCodigoActivity.this)
                        .setCallbacks(mCallBacks)
                        .build();


                PhoneAuthProvider.verifyPhoneNumber(options);

            }




        });



        mCallBacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {


            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {

            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                Toast.makeText(EnviarCodigoActivity.this, "Código enviado com sucesso!", Toast.LENGTH_SHORT).show();
                progressbarsetNumero.setVisibility(View.INVISIBLE);
                codesent=s;
                Intent intent = new Intent(EnviarCodigoActivity.this, VerificarCodigoActivity.class);
                intent.putExtra("otp",codesent);
                startActivity(intent);


            }
        };


    }


    @Override
    protected void onStart() {
        super.onStart();


        if (FirebaseAuth.getInstance().getCurrentUser()!=null)
        {
            Intent intent = new Intent(EnviarCodigoActivity.this, ChatActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        }

    }
}