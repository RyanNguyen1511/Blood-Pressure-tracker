package com.tuannguyen.bptracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {
    private Button input,preview,graph,overview;

    private TextView user;
    private DatabaseReference mDatabase;
    List<Integer> syslist,dialist,pullist,weilist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent intent = getIntent();
        user = findViewById(R.id.pro_user);
        String name = intent.getStringExtra("UserName");
        user.setText("Hello "+ name.toString() + " ! How are you today ? ");


        input = findViewById(R.id.btn_input);
        preview = findViewById(R.id.btn_preview);
        graph = findViewById(R.id.btn_graph);
        overview = findViewById(R.id.btn_overview);
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Users");
        syslist = new ArrayList<>();
    }

    public void openInput(View view) {
        Intent i =  new Intent(ProfileActivity.this, InputActivity.class);
        startActivities(new Intent[]{i});
    }


}