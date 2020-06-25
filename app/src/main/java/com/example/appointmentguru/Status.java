package com.example.appointmentguru;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Status extends AppCompatActivity {

    Button checkStatus;
    TextInputLayout name, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        checkStatus = findViewById(R.id.CheckStatus_btn);

        checkStatus.setOnClickListener(new View.OnClickListener() {

                private Boolean ValidateName ()
                  {
                    String val = name.getEditText().getText().toString();

                    if (val.isEmpty()) {
                        name.setError("Field cannot be empty");
                        return false;
                    } else {
                        name.setError(null);
                        name.setErrorEnabled(false);
                        return true;
                    }
                }

                private Boolean validatePhone() {

                    String val = phone.getEditText().getText().toString();

                    if (val.isEmpty()) {
                        phone.setError("Field cannot be empty");
                        return false;
                    } else {
                        phone.setError(null);
                        phone.setErrorEnabled(false);
                        return true;
                    }
                }
            @Override
            public void onClick(View v) {
                Toast.makeText(Status.this, "Please wait few second's", Toast.LENGTH_SHORT).show();
                if (!ValidateName()|!validatePhone()) {
                    return;
                } else {
                    final String userEnteredName = name.getEditText().getText().toString().trim();
                    final String userEnteredPhone = phone.getEditText().getText().toString().trim();

                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User");

                    Query checkUser = reference.orderByChild("name").equalTo(userEnteredName);

                    checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if (dataSnapshot.exists()) {

                                name.setError(null);
                                name.setErrorEnabled(false);

                                String phoneFromDB = dataSnapshot.child(userEnteredName).child("phone").getValue(String.class);

                                if (phoneFromDB.equals(userEnteredPhone)) {

                                    name.setError(null);
                                    name.setErrorEnabled(false);

                                    String nameFromDB = dataSnapshot.child(userEnteredName).child("name").getValue(String.class);
                                    String PhoneFromDB = dataSnapshot.child(userEnteredName).child("phone").getValue(String.class);
                                    String timeFromDB = dataSnapshot.child(userEnteredName).child("time").getValue(String.class);
                                    String dateFromDB = dataSnapshot.child(userEnteredName).child("date").getValue(String.class);
                                    String tokenFromDB = dataSnapshot.child(userEnteredName).child("token").getValue(String.class);
                                    String statusFromDB = dataSnapshot.child(userEnteredName).child("status").getValue(String.class);

                                    Intent intent = new Intent(getApplicationContext(), UserProfile.class);

                                    intent.putExtra("name", nameFromDB);
                                    intent.putExtra("phone", PhoneFromDB);
                                    intent.putExtra("time", timeFromDB);
                                    intent.putExtra("date", dateFromDB);
                                    intent.putExtra("token", tokenFromDB);
                                    intent.putExtra("status", statusFromDB);

                                    startActivity(intent);

                                    Toast.makeText(Status.this, "Now Check Your Status", Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(Status.this, "I think your phone.no is incorrect", Toast.LENGTH_SHORT).show();
                                    phone.setError("Please check your phone no");
                                    phone.requestFocus();
                                }
                            } else {
                                Toast.makeText(Status.this, "I think you enter wrong patient name", Toast.LENGTH_SHORT).show();
                                name.setError("No Appointment");
                                name.requestFocus();
                               }
                            }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            }
        });
    }
}

