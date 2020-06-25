package com.example.appointmentguru;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserProfile extends AppCompatActivity {

    TextInputLayout name, phone, date, time, token, status;
    Button takeAppointment, cancel_btn;
    TextView name_field, date_field;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        name = findViewById(R.id.name_profile);
        phone = findViewById(R.id.phone_profile);
        time = findViewById(R.id.time_profile);
        date = findViewById(R.id.date_profile);
        token = findViewById(R.id.token_profile);
        status = findViewById(R.id.status_profile);
        takeAppointment = findViewById(R.id.NewAppointment_btn);
        name_field = findViewById(R.id.Vehicle_field);
        date_field = findViewById(R.id.date_field);
        cancel_btn = findViewById(R.id.cancel_btn);


        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(UserProfile.this);

                builder.setMessage("  Are you sure cancel appointment ?").setCancelable(false).setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Toast.makeText(UserProfile.this, "your appointment is cancel", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        takeAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfile.this, Appointment.class);
                startActivity(intent);
            }
        });

        showAllUserDate();
    }

    private void showAllUserDate() {

        Intent intent = getIntent();
        String user_name = intent.getStringExtra("name");
        String user_phone = intent.getStringExtra("phone");
        String user_time = intent.getStringExtra("time");
        String user_date = intent.getStringExtra("date");
        String user_token = intent.getStringExtra("token");
        String user_status = intent.getStringExtra("status");

        name_field.setText(user_name);
        date_field.setText(user_token);
        name.getEditText().setText(user_name);
        phone.getEditText().setText(user_phone);
        time.getEditText().setText(user_time);
        date.getEditText().setText(user_date);
        token.getEditText().setText(user_token);
        status.getEditText().setText(user_status);

    }
}