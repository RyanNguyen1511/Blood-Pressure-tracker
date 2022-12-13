package com.tuannguyen.bptracker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InputActivity extends AppCompatActivity {
    private EditText inp_systolic,inp_diastolic,inp_pulse,inp_weight;
    private Button inp_submit;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        inp_systolic = findViewById(R.id.inp_systolic);
        inp_diastolic = findViewById(R.id.inp_diastolic);
        inp_pulse = findViewById(R.id.inp_pulse);
        inp_weight = findViewById(R.id.inp_weight);
        inp_submit = findViewById(R.id.inp_submit);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        inp_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String systolic = inp_systolic.getText().toString().trim();
                String diastolic = inp_diastolic.getText().toString().trim();
                String pulse = inp_pulse.getText().toString().trim();
                String weight = inp_weight.getText().toString().trim();

                mDatabase.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("systolic").push()
                        .setValue(systolic);
                mDatabase.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("diastolic").push()
                        .setValue(diastolic);
                mDatabase.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("pulse").push()
                        .setValue(pulse);
                mDatabase.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("weight").push()
                        .setValue(weight);



            }
        });

    }
}