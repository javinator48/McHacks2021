package com.example.mchack8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
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

public class LoginActivity extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private Button loginB;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {

        super.onStart();
        email = findViewById(R.id.emailInput);
        password = findViewById(R.id.password);
        loginB = findViewById(R.id.loginButton);

        loginB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
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

                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    DatabaseReference myRefMonday = database.getReference(userToken + "/Monday");
                                    myRefMonday.child("Medicine").setValue("SomeMedicine");
                                    DatabaseReference myRefTuesday = database.getReference(userToken + "/Tuesday");
                                    myRefTuesday.child("Medicine").setValue("SomeMedicine");
                                    DatabaseReference myRefWednesday = database.getReference(userToken + "/Wednesday");
                                    myRefWednesday.child("Medicine").setValue("SomeMedicine");
                                    DatabaseReference myRefThursday = database.getReference(userToken + "/Thursday");
                                    myRefThursday.child("Medicine").setValue("SomeMedicine");
                                    DatabaseReference myRefFriday = database.getReference(userToken + "/Friday");
                                    myRefFriday.child("Medicine").setValue("SomeMedicine");
                                    DatabaseReference myRefSaturday = database.getReference(userToken + "/Saturday");
                                    myRefSaturday.child("Medicine").setValue("SomeMedicine");
                                    DatabaseReference myRefSunday = database.getReference(userToken + "/Sunday");
                                    myRefSunday.child("Medicine").setValue("SomeMedicine");

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
            }

        });

    }
    //edit text
}