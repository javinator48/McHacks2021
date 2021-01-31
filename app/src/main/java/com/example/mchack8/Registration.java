package com.example.mchack8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class Registration extends AppCompatActivity {
    private EditText cPassword;
    private EditText password;
    private EditText newEmail;
    private Button registerB;
    private Button back;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
    }

    @Override
    protected void onStart() {
        super.onStart();
        cPassword = findViewById(R.id.confirm_password);
        password = findViewById(R.id.Register_password);
        newEmail = findViewById(R.id.RegisterEmail);
        registerB = findViewById(R.id.registerButton);
        mAuth = FirebaseAuth.getInstance();
        
        registerB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cPassword.getText().toString().equals(password.getText().toString())) {
                    mAuth.createUserWithEmailAndPassword(newEmail.getText().toString(), password.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information;
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        Toast.makeText(getApplicationContext(), "Registration succeed!\nlogging you in", Toast.LENGTH_LONG).show();
                                        mAuth.signInWithEmailAndPassword(newEmail.getText().toString(), password.getText().toString())
                                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                                        if (task.isSuccessful()){
                                                            // successful
                                                            Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_LONG).show();

                                                            // ___________ Esteban ____________
                                                            // Write a message to the database
                                                            // Write a message to the database
                                                            String userToken;
                                                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                                            userToken = user.getUid();

                                                            //Add the code below
                                                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                                                            // Delete code below if FirebaseUser already initialized
                                                            DatabaseReference myRefEthan = database.getReference("Users/" + userToken);
                                                            myRefEthan.child("Type").setValue("patient");


                                                            // Read from the database
                                                            DatabaseReference myRef = database.getReference(userToken + "/Monday/Medicine");
                                                            myRef.addValueEventListener(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                                    // This method is called once with the initial value and again
                                                                    // whenever data at this location is updated.
                                                                    String value = dataSnapshot.getValue(String.class);
                                                                    Toast.makeText(getApplicationContext(), "Value is: " + value, Toast.LENGTH_LONG).show();
                                                                }

                                                                @Override
                                                                public void onCancelled(DatabaseError error) {
                                                                    // Failed to read value
                                                                    Toast.makeText(getApplicationContext(), "Failed to read value.", Toast.LENGTH_LONG).show();
                                                                }
                                                            });

                                                            // ________________________________

                                                        } else {
                                                            // failed
                                                            Toast.makeText(getApplicationContext(), "Failed!", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });


//                                    updateUI(user);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(getApplicationContext(), "failed to register!", Toast.LENGTH_LONG).show();
//                                    updateUI(null);
                                    }

                                }
                            });
                }else{
                    Toast.makeText(getApplicationContext(), "confirm_password doesn't match the original password.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}