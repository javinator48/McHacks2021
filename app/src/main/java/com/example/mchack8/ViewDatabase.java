package com.example.mchack8;
import android.nfc.Tag;
import android.os.Bundle;
import android.view.View;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
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
import android.provider.ContactsContract;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import java.util.ArrayList;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class ViewDatabase extends AppCompatActivity {
    private static final String TAG = "ViewDatabase";
    private ListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser userJavin = FirebaseAuth.getInstance().getCurrentUser();
        String myUserIdJavin = userJavin.getUid();
// If want to get the info on Mondays do /Monday or (/Tuesday, /Wednesday, etc)
        DatabaseReference myRefList = database.getReference("Users/" + myUserIdJavin + "/Monday");
        Query myTopPostsQuery = myRefList.orderByChild("starCount");
        myTopPostsQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<String> t = new GenericTypeIndicator<String>() {
                };
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String value = postSnapshot.getValue(t);
                    showData(value);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, something wrong happen
            }
        });


    }

    private void showData(String input2) {

        DrugInformation uInfo = new DrugInformation();
        uInfo.setInfo(input2);
        //display all the information
        Log.d(TAG, "showData: info: " + uInfo.getInfo());
        ArrayList<String> array  = new ArrayList<>();
        array.add(uInfo.getInfo());
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, array);
        mListView.setAdapter(adapter);


    }
}