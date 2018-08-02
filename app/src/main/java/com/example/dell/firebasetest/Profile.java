package com.example.dell.firebasetest;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Profile extends AppCompatActivity {
    private Button signoutButton;
    private FirebaseAuth mAuth;
    private TextView activationTextField;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        signoutButton=(Button)findViewById(R.id.signoutButton);
        activationTextField=findViewById(R.id.activationTextField);
        mAuth = FirebaseAuth.getInstance();
        signoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });

        //for the activation
        final FirebaseUser user = mAuth.getCurrentUser();
        if (user.isEmailVerified()) {
            activationTextField.setVisibility(View.INVISIBLE);
        }
        else
        {
            activationTextField.setText("Email Not Verified (Click to Verify)");
            activationTextField.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(Profile.this, "Verification Email Sent", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }

    private void signOut() {
        FirebaseAuth.getInstance().signOut();
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }
}
