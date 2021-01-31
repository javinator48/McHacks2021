package com.example.mchack8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
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
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.Query;
import com.google.firebase.database.ChildEventListener;


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
                                    String myUserId;
                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                    myUserId = user.getUid();

                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    /*
                                    DatabaseReference myRefMonday = database.getReference(myUserId + "/Monday");
                                    myRefMonday.push().setValue("Intento2");
                                    myRefMonday.push().setValue("Intento3");
                                    myRefMonday.push().setValue("Intento4");
                                    myRefMonday.push().setValue("Intento5");
                                    myRefMonday.push().setValue("Intento6");
                                    myRefMonday.child("Medicine").setValue("SomeMedicine");
                                    DatabaseReference myRefTuesday = database.getReference(myUserId + "/Tuesday");
                                    myRefTuesday.child("Medicine").setValue("SomeMedicine");
                                    DatabaseReference myRefWednesday = database.getReference(myUserId + "/Wednesday");
                                    myRefWednesday.child("Medicine").setValue("SomeMedicine");
                                    DatabaseReference myRefThursday = database.getReference(myUserId + "/Thursday");
                                    myRefThursday.child("Medicine").setValue("SomeMedicine");
                                    DatabaseReference myRefFriday = database.getReference(myUserId + "/Friday");
                                    myRefFriday.child("Medicine").setValue("SomeMedicine");
                                    DatabaseReference myRefSaturday = database.getReference(myUserId + "/Saturday");
                                    myRefSaturday.child("Medicine").setValue("SomeMedicine");
                                    DatabaseReference myRefSunday = database.getReference(myUserId + "/Sunday");
                                    myRefSunday.child("Medicine").setValue("SomeMedicine");
                                    */

                                    // Read from the database
                                    DatabaseReference myRef = database.getReference(myUserId + "/Monday/Medicine");
                                    myRef.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            // This method is called once with the initial value and again
                                            // whenever data at this location is updated.
                                            String value = dataSnapshot.getValue(String.class);
                                            Toast.makeText(getApplicationContext(), "Value is: " + value, Toast.LENGTH_LONG).show();
                                            new Handler().postDelayed(new Runnable() { public void run() { } }, 5000);
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError error) {
                                            // Failed to read value
                                            Toast.makeText(getApplicationContext(), "Failed to read value.", Toast.LENGTH_LONG).show();
                                        }
                                    });

                                    // Read from a List
                                    DatabaseReference myRefList = database.getReference(myUserId + "/Monday");
                                    Query myTopPostsQuery = myRefList.orderByChild("starCount");
                                    // My top posts by number of stars
                                    myTopPostsQuery.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                                                new Handler().postDelayed(new Runnable() {
                                                    public void run() {
                                                        GenericTypeIndicator<String> t = new GenericTypeIndicator<String>() {};
                                                        String value = postSnapshot.getValue(t);
                                                        Toast.makeText(getApplicationContext(), "Value is: " + value, Toast.LENGTH_LONG).show();
                                                    } }, 5000);
                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                            // Getting Post failed, log a message
                                            Toast.makeText(getApplicationContext(), "Failed to read value.", Toast.LENGTH_LONG).show();
                                            // ...
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