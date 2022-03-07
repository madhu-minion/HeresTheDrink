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

public class RegisterActivity extends AppCompatActivity {

    String name, mobileNumber,emailId,password;
    EditText name_et, mobilenum_et,email_et,password_et;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();


        findViewById(R.id.register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name_et = findViewById(R.id.name_et);
                mobilenum_et = findViewById(R.id.phnum_et);
                email_et = findViewById(R.id.email_et);
                password_et = findViewById(R.id.pass_et);

                name = name_et.getText().toString().trim();
                mobileNumber =mobilenum_et.getText().toString().trim();
                emailId = email_et.getText().toString().trim();
                password = password_et.getText().toString().trim();

                if(isValid()){
                    mAuth.createUserWithEmailAndPassword(emailId, password)
                            .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    ProgressDialog progressDialog = new ProgressDialog(getApplicationContext());
                                    progressDialog.setMessage("Loading....");
                                    progressDialog.show();
                                    if (task.isSuccessful()) {
                                        progressDialog.dismiss();
                                        Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        progressDialog.dismiss();
                                        Toast.makeText(RegisterActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
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