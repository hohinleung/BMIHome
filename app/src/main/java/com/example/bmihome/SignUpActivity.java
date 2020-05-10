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

public class SignUpActivity extends AppCompatActivity {
    public EditText emailID, password;
    Button btnSignUp;
    TextView tvSignIn;
    FirebaseAuth mfirebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mfirebase = FirebaseAuth.getInstance();
        emailID = findViewById(R.id.emailtxt);
        password = findViewById(R.id.passwordtxt);
        btnSignUp = findViewById(R.id.SignUpbtn);
        tvSignIn = findViewById(R.id.Message);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailID.getText().toString().trim();
                String pwd = password.getText().toString().trim();
                if(email.isEmpty() && pwd.isEmpty()){
                    Toast.makeText(SignUpActivity.this, "Fields are empty!",Toast.LENGTH_SHORT).show();
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
                    mfirebase.createUserWithEmailAndPassword(email,pwd).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(SignUpActivity.this, "SignUp unsuccessfully!Try again!",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                startActivity(new Intent(SignUpActivity.this,HomeActivity.class));
                            }
                        }//end onComplete()
                    });//end onCompleteListener()
                }
                else{
                    Toast.makeText(SignUpActivity.this, "Unknown Error!",Toast.LENGTH_SHORT).show();
                }//end else
            }//end onclick()
        });//end setonclickListener()

        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }//end on create
}//end main
