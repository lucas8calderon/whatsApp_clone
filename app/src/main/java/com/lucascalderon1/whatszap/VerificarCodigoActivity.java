package com.lucascalderon1.whatszap;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;


public class VerificarCodigoActivity extends AppCompatActivity {


    TextView mchangenumber;
    EditText mgetotp;
    android.widget.Button mverifyotp;

    String enteredotp;

    FirebaseAuth firebaseAuth;
    ProgressBar progressbarsetCodigo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificar_codigo);

        mchangenumber = findViewById(R.id.changenumber);
        mverifyotp = findViewById(R.id.verifyotp);
        mgetotp = findViewById(R.id.getotp);
        progressbarsetCodigo = findViewById(R.id.progressbarsetCodigo);

        firebaseAuth=FirebaseAuth.getInstance();


        mchangenumber.setOnClickListener(v -> {
            Intent intent = new Intent(VerificarCodigoActivity.this, EnviarCodigoActivity.class);
            startActivity(intent);
        });


        mverifyotp.setOnClickListener(v -> {
            enteredotp = mgetotp.getText().toString();
            if (enteredotp.isEmpty()) {

                Toast.makeText(VerificarCodigoActivity.this, "Digite seu código", Toast.LENGTH_SHORT).show();

            } else {
                progressbarsetCodigo.setVisibility(View.VISIBLE);
                String coderecieved = getIntent().getStringExtra("otp");
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(coderecieved, enteredotp);
                signInWithPhoneCredencial(credential);


            }
        });

    }


    private void signInWithPhoneCredencial(PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                progressbarsetCodigo.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), "Sucesso ao fazer cadastro!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(VerificarCodigoActivity.this, PerfilActivity.class);
                startActivity(intent);
                finish();
            } else {
                if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {

                    progressbarsetCodigo.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), "Erro ao fazer cadastro!", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }


}