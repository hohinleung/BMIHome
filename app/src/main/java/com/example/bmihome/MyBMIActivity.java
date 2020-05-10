package com.example.bmihome;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MyBMIActivity extends AppCompatActivity {

    Button btnLogout,btnBMICal,btnMyBMI;
    TextView UserNametxt;
    FirebaseAuth mfirebase;
    Date currentTime = Calendar.getInstance().getTime();
    private DatabaseReference reff;
    private FirebaseAuth.AuthStateListener mAuthfirebase;

    private ArrayList<String> arrayList = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bmi);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        reff = FirebaseDatabase.getInstance().getReference();

        btnLogout = findViewById(R.id.logout);
        btnBMICal = findViewById(R.id.BMICal);
        UserNametxt = findViewById(R.id.UserNametxt);
        btnMyBMI = findViewById(R.id.MyBMI);


        if (user != null) {
            Toast.makeText(MyBMIActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
            for (UserInfo profile : user.getProviderData()) {
                String email = profile.getEmail();
            }
        } else {
            Toast.makeText(MyBMIActivity.this, "You need to login again", Toast.LENGTH_SHORT).show();
            Intent intToMain = new Intent(MyBMIActivity.this, MainActivity.class);
            startActivity(intToMain);
        }

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intToMain = new Intent(MyBMIActivity.this, MainActivity.class);
                startActivity(intToMain);
            }
        });

        btnBMICal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cal = new Intent(MyBMIActivity.this, BMICalActivity.class);
                startActivity(cal);
            }
        });

        btnMyBMI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent b = new Intent(MyBMIActivity.this, MyBMIActivity.class);
                startActivity(b);
            }
        });

    }//end Oncreate()
}
