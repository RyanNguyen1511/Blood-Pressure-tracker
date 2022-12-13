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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText login_email, login_pass;
    private TextView register;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private Button btn_login;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        register = findViewById(R.id.register);
        register.setOnClickListener(this);

        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);

        login_email = findViewById(R.id.login_email);
        login_pass = findViewById(R.id.login_password);

        progressBar = findViewById(R.id.login_progressbar);

        mAuth = FirebaseAuth.getInstance();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register:
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_login:
                userLogin();
        }
    }
    private void userLogin(){
        String email = login_email.getText().toString().trim();
        String pass = login_pass.getText().toString().trim();


        if(email.isEmpty()){
            login_email.setError("Email is Required!!!");
            login_email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            login_email.setError("Please enter the valid email!!!");
            login_email.requestFocus();
            return;
        }
        if(pass.isEmpty() || pass.length()<6){
            login_pass.setError("Password length should be more than 6 characters!!!");
            login_pass.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                    intent.putExtra("UserName","Tuan");
                    startActivities(new Intent[]{intent});
                    progressBar.setVisibility(View.GONE);
                }else{
                    Toast.makeText(MainActivity.this,"Fail to login! Please Check your credentials!" ,Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}