package hackerbois.bruinstrooms;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class RestroomActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference ref;
    private DatabaseReference thisRestroom;
    private Restroom room;
    private String restroomName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restroom);

    } //end of onCreate

    @Override
    protected void onResume() {
        super.onResume();

        database = FirebaseDatabase.getInstance();

        restroomName = "MS4317M"; //TODO: temporary place holder, when intent calls this activity pass in the restroom name

        ref = database.getReference();
        thisRestroom = ref.child("restrooms/" + restroomName);

        thisRestroom.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue(String.class);
                String gender = dataSnapshot.child("gender").getValue(String.class);
                String addon;
                if (gender.equals("male")) {
                    addon = " - MALE";
                }
                else if (gender.equals("female")) {
                    addon = " - FEMALE";
                }
                else {
                    addon = " - ALL GENDER";
                }
                TextView title = (TextView) findViewById(R.id.RestroomName);
                title.setText(name + addon);

                TextView features = (TextView) findViewById(R.id.RestroomFeatures);
                String urinals = dataSnapshot.child("urinals").getValue(String.class);

                String hasHandicap = dataSnapshot.child("hasHandicap").getValue(String.class);
                String mirrors = dataSnapshot.child("mirrors").getValue(String.class);
                String sinks = dataSnapshot.child("sinks").getValue(String.class);
                String stalls = dataSnapshot.child("stalls").getValue(String.class);
                String urinalDividers = dataSnapshot.child("urinalDividers").getValue(String.class);

                String message = "Urinals: " + urinals + "\nUrinal Dividers: " + urinalDividers + "\nStalls: " + stalls + "\nHandicap Accesible: " + hasHandicap + "\n Sinks: " + sinks+ "\nMirrors: " + mirrors;
                features.setText(message);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //error
            }
        });
    }

}
