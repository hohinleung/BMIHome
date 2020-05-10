package com.example.bmihome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    public EditText emailID, password;
    Button btnSignIn;
    TextView tvSignUp;
    FirebaseAuth mfirebase;
    private FirebaseAuth.AuthStateListener mAuthfirebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mfirebase = FirebaseAuth.getInstance();
        emailID = findViewById(R.id.emailtxt);
        password = findViewById(R.id.passwordtxt);
        btnSignIn = findViewById(R.id.SignInbtn);
        tvSignUp = findViewById(R.id.Message);

        mAuthfirebase = new FirebaseAuth.AuthStateListener() {
            FirebaseUser mFirebaseUser = mfirebase.getCurrentUser();

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mfirebase.getCurrentUser();
                if(mFirebaseUser != null){
                    Toast.makeText(MainActivity.this, "Login successfully!",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(MainActivity.this, "Please login again!",Toast.LENGTH_SHORT).show();
                }
            }
        };

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailID.getText().toString().trim();
                String pwd = password.getText().toString().trim();
                if(email.isEmpty() && pwd.isEmpty()){
                    Toast.makeText(MainActivity.this, "Fields are empty!",Toast.LENGTH_SHORT).show();
                }
                else if (pwd.isEmpty()){
                    password.setError("Please enter email id!");
                    password.requestFocus();
                }
                else if(email.isEmpty()){
                    emailID.setError("Please enter email id!");
                    emailID.requestFocus();
                }
                else if(!(email.isEmpty() && pwd.isEmpty())){
                    mfirebase.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(MainActivity.this, "Login Error!",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(MainActivity.this, "Login Successfully!",Toast.LENGTH_SHORT).show();
                                Intent intToHome = new Intent(MainActivity.this, HomeActivity.class);
                                startActivity(intToHome);
                            }//login success
                        }
                    });
                }
                else{
                    Toast.makeText(MainActivity.this, "Unknown Error!",Toast.LENGTH_SHORT).show();
                }//end else
            }
        });

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intSignUp = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intSignUp);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mfirebase.addAuthStateListener(mAuthfirebase);
    }
}
