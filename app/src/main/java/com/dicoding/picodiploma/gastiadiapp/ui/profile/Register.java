package com.dicoding.picodiploma.gastiadiapp.ui.profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dicoding.picodiploma.gastiadiapp.MainActivity;
import com.dicoding.picodiploma.gastiadiapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    EditText FullName,Email,Password,NoHp;
    Button Register;
    TextView Login;
    FirebaseAuth mAuth;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        FullName = findViewById(R.id.eFullName);
        Email = findViewById(R.id.eEmail);
        Password = findViewById(R.id.ePassword);
        NoHp = findViewById(R.id.eNoHp);
        Register = findViewById(R.id.btnRegister);
        Login = findViewById(R.id.tCreate);
        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);

        if(mAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = Email.getText().toString().trim();
                String password = Password.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    Email.setError("Email is Required");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Password.setError("Password is Required");
                    return;
                }
                if(password.length() < 6){
                    Password.setError("Password Must be >= 6 Characters");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                // register the user in firebase

                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Register.this,"User Created.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),Login.class));
                        }else {
                            Toast.makeText(Register.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }

                    }
                });
            }
        });
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });
    }
}