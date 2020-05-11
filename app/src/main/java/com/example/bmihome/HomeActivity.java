package com.example.bmihome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

public class HomeActivity extends AppCompatActivity {

    Button btnLogout,btnBMICal,btnMyBMI;
    TextView UserNametxt;
    FirebaseAuth mfirebase;
    private FirebaseAuth.AuthStateListener mAuthfirebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        btnLogout = findViewById(R.id.logout);
        btnBMICal = findViewById(R.id.BMICal);
        UserNametxt = findViewById(R.id.UserNametxt);
        btnMyBMI = findViewById(R.id.MyBMI);

        if (user != null) {
            Toast.makeText(HomeActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
            for (UserInfo profile : user.getProviderData()) {
                String email = profile.getEmail();
            }
        } else {
            Toast.makeText(HomeActivity.this, "You need to login again", Toast.LENGTH_SHORT).show();
            Intent intToMain = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(intToMain);
        }

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intToMain = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intToMain);
            }
        });

        btnBMICal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cal = new Intent(HomeActivity.this, BMICalActivity.class);
                startActivity(cal);
            }
        });

        btnMyBMI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent b = new Intent(HomeActivity.this, MyBMIActivity.class);
                startActivity(b);
            }
        });
    }
}
