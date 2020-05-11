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
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BMICalActivity extends AppCompatActivity {

    Button btnLogout,btnBMICal,btnCal,btnUpload,btnMyBMI;
    TextView UserNametxt,Resulttxt,Advicetxt;
    EditText height,weight;
    DatabaseReference reff;
    DatabaseReference oldreff;
    DatabaseReference getreff;
    Date currentTime = Calendar.getInstance().getTime();
    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
    String formattedDate = df.format(currentTime);
    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    String date,id,email,bmi;
    BMI sbmi;
    Double num,xmi;
    long maxid=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmical);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        btnLogout = findViewById(R.id.logout);
        btnBMICal = findViewById(R.id.BMICal);
        UserNametxt = findViewById(R.id.UserNametxt);
        btnCal = findViewById(R.id.Cal);
        btnUpload = findViewById(R.id.upload);
        height = findViewById(R.id.Height);
        weight = findViewById(R.id.Weight);
        Resulttxt = findViewById(R.id.Resulttxt);
        Advicetxt = findViewById(R.id.advice);
        btnMyBMI = findViewById(R.id.MyBMI);
        sbmi = new BMI();

        btnUpload.setVisibility(View.INVISIBLE);

        if (user != null) {
            for (UserInfo profile : user.getProviderData()) {
                String email = profile.getEmail();
                UserNametxt.setText(email);
            }
        } else {
            Toast.makeText(BMICalActivity.this, "You need to login again", Toast.LENGTH_SHORT).show();
            Intent intToMain = new Intent(BMICalActivity.this, MainActivity.class);
            startActivity(intToMain);
        }

        String name= UserNametxt.getText().toString().trim();
        reff = FirebaseDatabase.getInstance().getReference().child("User");

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    maxid = (dataSnapshot.getChildrenCount());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intToMain = new Intent(BMICalActivity.this, MainActivity.class);
                startActivity(intToMain);
            }
        });

        btnBMICal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cal = new Intent(BMICalActivity.this, BMICalActivity.class);
                startActivity(cal);
            }
        });

        btnCal.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                double cheight = Double.parseDouble(height.getText().toString().trim());
                double cweight = Double.parseDouble(weight.getText().toString().trim());
                if(cheight==0){
                    height.setError("Please enter height!");
                    height.requestFocus();
                }
                else if(cweight==0){
                    weight.setError("Please enter height!");
                    weight.requestFocus();
                }
                else if(cheight!=0 && cweight!=0 && cheight>=100 && cweight>=20){
                    double bmi = cweight / ((cheight/100)*(cheight/100));
                    if(bmi<18.5){
                        Advicetxt.setText("You are too light!");
                    }
                    else if(bmi>18.5 && bmi<=24){
                        Advicetxt.setText("Congraduation!You are in normal state!");
                    }
                    else if((bmi>24 && bmi<=27)){
                        Advicetxt.setText("You are little fat!");
                    }
                    else if((bmi>27 && bmi<=30)){
                        Advicetxt.setText("You are too fat!");
                    }
                    Resulttxt.setText(Double.toString(bmi));
                    btnUpload.setVisibility(View.VISIBLE);
                    num = bmi;
                }
            }//end onclick()
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get new record to old record
                oldreff = FirebaseDatabase.getInstance().getReference().child("User").child("new:"+uid);
                oldreff.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.exists()) {
                            id = dataSnapshot.child("id").getValue().toString();
                            date = dataSnapshot.child("date").getValue().toString();
                            email = dataSnapshot.child("email").getValue().toString();
                            bmi = dataSnapshot.child("bmi").getValue().toString();
                            xmi = Double.parseDouble(bmi);
                        }//checking

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(BMICalActivity.this, "No data", Toast.LENGTH_SHORT).show();
                    }
                });
                //set old record
                getreff = FirebaseDatabase.getInstance().getReference().child("User");
                if((id!=null&&date!=null&&email!=null&&bmi!=null)) {
                    sbmi.setId(id);
                    sbmi.setBmi(xmi);
                    sbmi.setEmail(email);
                    sbmi.setDate(date);
                    getreff.child(String.valueOf("old:" + uid)).setValue(sbmi);
                }

                //new record
                reff = FirebaseDatabase.getInstance().getReference().child("User");
                String semail = String.valueOf(UserNametxt.getText().toString().trim());
                String sdate = formattedDate;

                sbmi.setId(uid);
                sbmi.setBmi(num);
                sbmi.setEmail(semail);
                sbmi.setDate(sdate);
                reff.child(String.valueOf("new:"+uid)).setValue(sbmi);
                Toast.makeText(BMICalActivity.this, "Upload successfully", Toast.LENGTH_SHORT).show();
            }
        });

        btnMyBMI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent b = new Intent(BMICalActivity.this, MyBMIActivity.class);
                startActivity(b);
            }
        });
    }
}
