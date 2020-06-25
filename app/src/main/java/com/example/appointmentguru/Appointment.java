package com.example.appointmentguru;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Appointment extends AppCompatActivity {

    Button Fix;
    TextInputLayout Name, Phone, Time, Date , Token, Status;

    FirebaseDatabase rootNode;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        Fix = findViewById(R.id.fix);
        Name = findViewById(R.id.name);
        Phone = findViewById(R.id.phone);
        Time = findViewById(R.id.time);
        Date = findViewById(R.id.date);
        Token = findViewById(R.id.token);
        Status = findViewById(R.id.status);


        Fix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("User");

                String name = Name.getEditText().getText().toString();
                String phone = Phone.getEditText().getText().toString();
                String time = Time.getEditText().getText().toString();
                String date = Date.getEditText().getText().toString();
                String token = Token.getEditText().getText().toString();
                String status = Status.getEditText().getText().toString();

                HelperClass helperClass = new HelperClass(name, phone, time, date, token, status);
                reference.child(name).setValue(helperClass);
                Toast.makeText(Appointment.this, "Appointment request is send", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
