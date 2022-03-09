package com.greymatter.heresthedrink;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    String name, mobileNumber,emailId,password;
    EditText name_et, mobilenum_et,email_et,password_et;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name_et = findViewById(R.id.name_et);
        mobilenum_et = findViewById(R.id.phnum_et);
        email_et = findViewById(R.id.email_et);
        password_et = findViewById(R.id.pass_et);

        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name = name_et.getText().toString().trim();
                mobileNumber =mobilenum_et.getText().toString().trim();
                emailId = email_et.getText().toString().trim();
                password = password_et.getText().toString().trim();

                if(isValid()){
                    register();
                }
            }
        });

    }

    private void register() {
        progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setTitle("Loading....");
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(emailId, password)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            saveData();
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void saveData() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("user");

        HashMap<String,Object> map = new HashMap<>();
        map.put("name",name);
        map.put("phone",mobileNumber);
        map.put("email",emailId);
        map.put("password",password);
        map.put("uid",FirebaseAuth.getInstance().getCurrentUser().getUid());

        ref.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressDialog.dismiss();

                if (task.isSuccessful()){
                    Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isValid() {
        if(name.isEmpty()){
            name_et.setError("Enter the full Name");
            return false;
        }
        if(emailId.isEmpty()){
            email_et.setError("Invalid EmailID");
            return false;
        }
        if (mobileNumber.length() != 10){
            mobilenum_et.setError("Invalid mobile number");
            return false;
        }
        if (password.length() < 8){
            password_et.setError("Password must be 8-16 characters");
            return false;
        }
        return true;
    }

    public void navRegister(View view) {
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
    }
}