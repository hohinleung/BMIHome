package com.example.bmihome;
//Auther:hohinleung
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
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MyBMIActivity extends AppCompatActivity {

    Button btnLogout,btnBMICal,btnMyBMI,btnShow;
    TextView UserNametxt,Newtxt,Oldtxt,Time,BMI,NTime,NBMI,compare;
    private DatabaseReference reff,newreff;
    String date,bmi,Ndate,Nbmi;
    Double xmi,Nxmi,result;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bmi);

        reff = FirebaseDatabase.getInstance().getReference();
        newreff = FirebaseDatabase.getInstance().getReference();

        btnLogout = findViewById(R.id.logout);
        btnBMICal = findViewById(R.id.BMICal);
        UserNametxt = findViewById(R.id.UserNametxt);
        btnMyBMI = findViewById(R.id.MyBMI);
        Newtxt = findViewById(R.id.Newtxt);
        Oldtxt = findViewById(R.id.Oldtxt);
        Time = findViewById(R.id.Time);
        BMI = findViewById(R.id.BMI);
        NTime = findViewById(R.id.NTime);
        NBMI = findViewById(R.id.NBMI);
        btnShow = findViewById(R.id.show);
        compare = findViewById(R.id.compare);


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

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newreff = FirebaseDatabase.getInstance().getReference().child("User").child("new:"+uid);
                newreff.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.exists()) {
                            Ndate = dataSnapshot.child("date").getValue().toString();
                            Nbmi= dataSnapshot.child("bmi").getValue().toString();
                            NBMI.setText("Your BMI: "+Nbmi);
                            NTime.setText("Date: "+Ndate);

                            xmi = Double.parseDouble(bmi);
                            Nxmi = Double.parseDouble(Nbmi);

                            result = (Nxmi/xmi)*100-100;
                            String re = Double.toString(result);
                            compare.setText("BMI change: "+re+"%");
                        }
                        else{
                            NBMI.setText("No data");
                            NTime.setText("No data");
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                reff = FirebaseDatabase.getInstance().getReference().child("User").child("old:"+uid);
                reff.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.exists()) {
                            date = dataSnapshot.child("date").getValue().toString();
                            bmi= dataSnapshot.child("bmi").getValue().toString();
                            BMI.setText("Your BMI: " + bmi);
                            Time.setText("Date: " + date);
                        }
                        else{
                            BMI.setText("No data");
                            Time.setText("No data");
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }//end Oncreate()
}
