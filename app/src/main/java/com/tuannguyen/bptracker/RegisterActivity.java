package com.tuannguyen.bptracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText res_fullname,res_email,res_pass;
    private Button btn_register;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
         if (mAuth.getCurrentUser()!=null){
             finish();
             return;
         }
        res_fullname = findViewById(R.id.res_fullname);
        res_email = findViewById(R.id.res_email);
        res_pass = findViewById(R.id.res_pass);
        btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);
        progressBar = findViewById(R.id.res_progressbar);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.banner:
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivities(new Intent[]{intent});
                break;
            case R.id.btn_register:
                RegisterUser();
                break;
        }
    }

    private void RegisterUser() {
        String fullname = res_fullname.getText().toString().trim();
        String email = res_email.getText().toString().trim();
        String pass = res_pass.getText().toString().trim();
        List<Integer> systolic,diastolic,pulse,weight;
        systolic = Collections.emptyList();
        diastolic = Collections.emptyList();
        pulse = Collections.emptyList();
        weight = Collections.emptyList();
        if(fullname.isEmpty()){
            res_fullname.setError("Fullname is Required!!!");
            res_fullname.requestFocus();
            return;
        }
        if(email.isEmpty()){
            res_email.setError("Email is Required!!!");
            res_email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            res_email.setError("Please enter the valid email!!!");
            res_email.requestFocus();
            return;
        }
        if(pass.isEmpty() || pass.length()<6){
            res_pass.setError("Password length should be more than 6 characters!!!");
            res_pass.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email,pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User(fullname, email,systolic,diastolic,pulse,weight);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(RegisterActivity.this,"User has been registerd successfully!",Toast.LENGTH_LONG).show();
                                                progressBar.setVisibility(View.GONE);
                                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                                startActivities(new Intent[]{intent});
                                            }else{
                                                Toast.makeText(RegisterActivity.this,"Fail to register new User! Try again!",Toast.LENGTH_LONG).show();
                                                progressBar.setVisibility(View.GONE);
                                            }
                                        }
                                    });
                        }else{
                            Toast.makeText(RegisterActivity.this,"Fail to register new User! Try again 2!" ,Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

    }
}